package com.stackerrors.service;

import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.AskQuestionRequest;
import com.stackerrors.dtos.request.UpdateQuestionRequest;
import com.stackerrors.dtos.request.UploadImageRequest;
import com.stackerrors.dtos.response.QuestionDto;
import com.stackerrors.dtos.response.QuestionListItemDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.QuestionDtoConvertor;
import com.stackerrors.mapper.QuestionListItemDtoConvertor;
import com.stackerrors.model.Image;
import com.stackerrors.model.Question;
import com.stackerrors.model.Tag;
import com.stackerrors.model.User;
import com.stackerrors.repository.QuestionRepository;
import com.stackerrors.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final TagService tagService;
    private final CloudServiceInter cloudServiceInter;
    private final QuestionDtoConvertor questionDtoConvertor;
    private final QuestionListItemDtoConvertor questionListItemDtoConvertor;
    private final AuthService authService;
    private final UserRepository userRepository;


    public QuestionService(QuestionRepository questionRepository,
                           UserService userService,
                           TagService tagService,
                           @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter,
                           QuestionDtoConvertor questionDtoConvertor,
                           QuestionListItemDtoConvertor questionListItemDtoConvertor,
                           AuthService authService, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.tagService = tagService;
        this.cloudServiceInter = cloudServiceInter;
        this.questionDtoConvertor = questionDtoConvertor;
        this.questionListItemDtoConvertor = questionListItemDtoConvertor;
        this.authService = authService;
        this.userRepository = userRepository;
    }


    @Transactional
    public void askQuestion(AskQuestionRequest request ) {

        User user = authService.getAuthenticatedUser();
        List<Tag> tags = questionsTags(request.getTags());

        Question question = Question.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .answered(false)
                .creationDate(new Date(System.currentTimeMillis()))
                .isActive(true)
                .draft(request.isDraft())
                .views(0)
                .tags(tags)
                .build();

        if(request.getFiles()!=null) {
            List<Image> images = questionsImages(request.getFiles() , question);
            question.setQuestionImages(images);
        }

        questionRepository.save(question);
    }




    @Transactional
    public void updateQuestion(UpdateQuestionRequest request){

        User user = authService.getAuthenticatedUser();
        Question question = findById(request.getQuestionId());


        if (question.getUser().getId() != user.getId()){

            throw GenericException.builder()
                    .errorMessage("Suali update etme huququna sahib deyilsiniz")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .build();
        }

        question.setTitle(request.getTitle());
        question.setDescription(request.getText());
        question.setTags(questionsTags(request.getTags()));
        question.setUpdateDate(new Date(System.currentTimeMillis()));
        questionRepository.save(question);
    }







    //  refactor
    @Transactional
    public void uploadImage(UploadImageRequest request){

        User user = authService.getAuthenticatedUser();
        Question question = findById(request.getQuestionId());


        if (question.getUser().getId() != user.getId()){

            throw GenericException.builder()
                    .errorMessage("Sekil yuklemek huququna sahib deyilsiniz")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .build();
        }

        if (request.getFiles() != null){
            List<Image> images = questionsImages(request.getFiles() , question);
            question.setQuestionImages(images);
        }
        questionRepository.save(question);
    }






    @SneakyThrows
    @Transactional
    public void deleteQuestion(int questionId){

        User user = authService.getAuthenticatedUser();
        Question question = findById(questionId);


        if (user.getId() != question.getUser().getId()) {

            throw GenericException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage("ACCESS DENIED")
                    .build();
        }


        List<String> publishIDs = question.getQuestionImages().stream()
                .map(Image::getPublishId)
                .collect(Collectors.toList());

        for (String publisID : publishIDs) {
            cloudServiceInter.deleteImage(publisID);
        }
        questionRepository.delete(question);



    }







    public QuestionDto getQuestionById(int id){

       final Question question = findById(id);

       QuestionDto dto = questionDtoConvertor.convertToQuestionDto(question);

        return dto;
    }


    @Transactional
    public void setActive(int questionId, boolean state){

        User user = authService.getAuthenticatedUser();
        Question question = findById(questionId);

        if (question.getUser().getId() != user.getId()){
            throw GenericException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage("ACCESS DENIED")
                    .build();
        }
        question.setActive(state);
    }



    @Transactional
    public void setAnswered(int questionId, boolean state){
        User user = authService.getAuthenticatedUser();
        Question question = findById(questionId);

        if (question.getUser().getId() != user.getId()){
            throw GenericException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage("ACCESS DENIED")
                    .build();
        }
        question.setAnswered(state);
    }


    public List<QuestionListItemDto> getAllQuestions(int pageNo , int size){

        final List<Question> questions = questionRepository.findAllByDraftFalse(PageRequest.of(pageNo - 1, size));

        List<QuestionListItemDto> dtos =  questions.stream()
                //.map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .map(questionListItemDtoConvertor::convertToQuestionListItemDto)
                .collect(Collectors.toList());

       return dtos;
    }




    public List<QuestionListItemDto> getQuestionsAnsweredTrue(int pageNo , int size){

        final List<Question> questions = questionRepository.findByAnsweredTrueAndDraftFalse(PageRequest.of(pageNo - 1, size)).orElseThrow(
                () ->
                GenericException.builder()
                        .errorCode(ErrorCode.QUESTION_NOT_FOUND)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("question not found")
                        .build());

        List<QuestionListItemDto> dtos =  questions.stream()
                .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return dtos;
    }



    // stream filter vs jpql query speed test
    public List<QuestionListItemDto> getQuestionsAnsweredFalse(int pageNo , int size){

        final List<Question> questions = questionRepository.findAllByDraftFalse(PageRequest.of(pageNo - 1, size));

        List<Question> afterFilter = questions.stream().filter(n -> n.isAnswered() == false).collect(Collectors.toList());

        List<QuestionListItemDto> dtos =  afterFilter.stream()
                .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return dtos;
    }



    public List<QuestionListItemDto> getQuestionsActiveTrue(int pageNo , int size){

        final List<Question> questions = questionRepository.findByIsActiveTrueAndDraftFalse( PageRequest.of(pageNo - 1, size)).orElseThrow(
                () ->
                        GenericException.builder()
                                .errorCode(ErrorCode.QUESTION_NOT_FOUND)
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .errorMessage("question not found")
                                .build()

        );


        List<QuestionListItemDto> dtos =  questions.stream()
                .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return dtos;
    }



    public List<QuestionListItemDto> getQuestionsActiveFalse(int pageNo , int size){

        final List<Question> questions = questionRepository.findByIsActiveFalseAndDraftFalse( PageRequest.of(pageNo - 1, size)).orElseThrow();


        List<QuestionListItemDto> dtos =  questions.stream()
                .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return dtos;
    }





    public List<QuestionListItemDto> creationDateOrderByAsc(int pageNo , int size){

        Sort sort = Sort.by(Sort.Direction.ASC , "creationDate");

        final List<Question> questions = questionRepository.findAllByDraftFalse(PageRequest.of(pageNo - 1, size , sort )  );

        List<QuestionListItemDto> dtos =  questions.stream()
                .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return dtos;

    }





    public List<QuestionListItemDto> creationDateOrderByDesc(int pageNo , int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");

        final List<Question> questions = questionRepository.findAllByDraftFalse(PageRequest.of(pageNo - 1, size , sort)  );

        List<QuestionListItemDto> dtos =  questions.stream()
                .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return dtos;

    }



    public List<QuestionListItemDto> searchQuestion(String search){

        List<Question> questions = questionRepository.getByTitleLike(search);


        List<QuestionListItemDto> result = questions
                .stream()
                .filter(n->n.isDraft()==false)
                .map(n -> questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());
        return result;
    }




    public List<QuestionListItemDto> getAllQuestionByUserId(){

        User user = authService.getAuthenticatedUser();

        List<Question> questions = questionRepository.findAllByUser_IdAndDraftFalse(user.getId());

        List<QuestionListItemDto> result = questions.stream()
                .map(n -> questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return result;
    }


    public List<QuestionListItemDto> getAllMyQuestionFromDraft(){

        User user = authService.getAuthenticatedUser();

        List<Question> questions = questionRepository.findAllByUser_IdAndDraftTrue(user.getId());

        List<QuestionListItemDto> result = questions.stream()
                .map(n -> questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());

        return result;
    }




    @Transactional
    public void changeDraftFalse(int questionId){
        User user = authService.getAuthenticatedUser();
        Question question = findById(questionId);

        if (user.getId() != question.getUser().getId()){
            throw GenericException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .errorMessage("Bu hisseye giris icazeniz yoxdur")
                    .build();
        }

        question.setDraft(false);
        questionRepository.save(question);
    }





    public List<QuestionListItemDto> findAllByTags_TagName(String  name){
        List<Question> questions = questionRepository.findAllByTags_TagNameAndDraftFalse(name);
        return questions.stream()
                .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                .collect(Collectors.toList());
    }



    @Transactional
    public void increaseViews(int questionId){
        Question question = findById(questionId);
        Integer viewCount = question.getViews() + 1;
        question.setViews(viewCount);
        questionRepository.save(question);
    }








    protected List<Image> questionsImages(List<MultipartFile> files , Question question){

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {
            final Map<String , String> cloudData = cloudServiceInter.uploadImage(file);
            final String imageUrl = cloudData.get("secure_url");
            final String publishId = cloudData.get("public_id");
            images.add(
                    Image.builder()
                            .imageUrl(imageUrl)
                            .publishId(publishId)
                            .question(question)
                            .user(null)
                            .error(null)
                            .comment(null)
                            .build()
            );
        }
        return images;

    }





    private List<Tag> questionsTags(List<Integer> tagIDs){

        List<Tag> tags = new ArrayList<>();

        for(Integer tagId : tagIDs){
            final Tag tagFromDb = tagService.findById(tagId);
            tags.add(tagFromDb);
        }

        return tags;
    }



    protected Question findById(int id){
        return questionRepository.findById(id).orElseThrow(
                () -> GenericException.builder()
                                .errorCode(ErrorCode.QUESTION_NOT_FOUND)
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .errorMessage("question not found")
                                .build() );
    }

    @Transactional
    public void likeQuestion(int questionId){

        Question question = findById(questionId);
        User user = authService.getAuthenticatedUser();
        List<User> likedUsers = question.getLikedUsers();
        List<User> dissLikedUsers = question.getDissLikedUsers();

        for(User element : dissLikedUsers){
            if(user.getId() == element.getId()){
              undoDissLike(questionId);
            }
        }

        for (User element : likedUsers){
            if (element.getId() == user.getId()){
                throw GenericException.builder()
                        .errorCode(ErrorCode.ALREADY_LIKED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage("Siz zaten like etmisiz")
                        .build();
            }
        }


        user.getLikesQuestions().add(question);
        userRepository.save(user);
    }








    @Transactional
    public void dissLikeQuestion(int questionId){

        Question question = findById(questionId);
        User user = authService.getAuthenticatedUser();
        List<User> likedUsers = question.getLikedUsers();
        List<User> dissLikedUsers = question.getDissLikedUsers();


        for(User element : likedUsers ){
            if (user.getId() == element.getId()){
                undoLike(questionId);
            }
        }



        for (User element : dissLikedUsers){
            if (element.getId() == user.getId()){
                throw GenericException.builder()
                        .errorCode(ErrorCode.ALREADY_LIKED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage("Siz zaten like etmisiz")
                        .build();
            }
        }


        user.getDissLikedQuestions().add(question);
        userRepository.save(user);
    }





    public void undoLike(int questionId){
        Question question = findById(questionId);
        User user = authService.getAuthenticatedUser();
        user.getLikesQuestions().remove(question);
        userRepository.save(user);
    }



    public void undoDissLike(int questionId ){
        Question question = findById(questionId);
        User user = authService.getAuthenticatedUser();
        user.getDissLikedQuestions().remove(question);
        userRepository.save(user);
    }




}
