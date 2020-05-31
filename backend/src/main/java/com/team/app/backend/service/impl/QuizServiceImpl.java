package com.team.app.backend.service.impl;

import com.team.app.backend.dto.*;
import com.team.app.backend.exception.QuizNotFoundException;
import com.team.app.backend.persistance.dao.*;
import com.team.app.backend.persistance.model.*;
import com.team.app.backend.service.NotificationService;
import com.team.app.backend.service.QuizService;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final long NOTIFICATION_APPROVED = 2L;

    private final QuizDao quizDao;
    private final NotificationDao notificationDao;

    @Autowired
    private NotificationService notificationService;


    private final UserActivityDao userActivityDao;

    private final UserDao userDao;

    private final QuestionDao questionDao;

    private final OptionDao optionDao;

    private final QuizCategoryDao quizCategoryDao;

    private final MessageSource messageSource;

    private final UserService userService;

    @Autowired
    public QuizServiceImpl(QuizDao quizDao, NotificationDao notificationDao, UserActivityDao userActivityDao, UserDao userDao, QuestionDao questionDao, OptionDao optionDao, QuizCategoryDao quizCategoryDao, MessageSource messageSource, UserService userService) {
        this.quizDao = quizDao;
        this.notificationDao = notificationDao;
        this.userActivityDao = userActivityDao;
        this.userDao = userDao;
        this.questionDao = questionDao;
        this.optionDao = optionDao;
        this.quizCategoryDao = quizCategoryDao;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @Override
    public void updateQuiz(QuizUpdateDto dto) throws QuizNotFoundException {
        Quiz quiz = quizDao.get(dto.getId());
        if (quiz == null) {
            throw new QuizNotFoundException("Quiz with id " + dto.getId() + " not found in the DB.");
        }
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setImage(dto.getImage());
        quizDao.update(quiz);
    }

    @Override
    public Long addDefQuestion(QuestionDefAddDto questionDefAddDto) {
        Long id = addQuestion(questionDefAddDto);
        for (DefOptionDto defOptionDto: questionDefAddDto.getOptions()) {
            DefaultQuest defaultQuest=new DefaultQuest();
            defaultQuest.setAnswer(defOptionDto.getAnswer());
            defaultQuest.setImage(defOptionDto.getImage());
            defaultQuest.setQuest_id(id);
            optionDao.addDefaultOption(defaultQuest);
        }
        return id;

    }

    @Override
    public Long addQuestion(QuestionDto questionDto){
        Question question = new Question();
        question.setQuiz_id((long)questionDto.getQuiz_id());
        question.setType(questionDto.getType());
        question.setImage(questionDto.getImage());
        question.setText(questionDto.getText());
        if(questionDto.getMax_points()==null){
            question.setMax_points(10);
        }else{
            question.setMax_points(questionDto.getMax_points());
        }
        question.setTime(questionDto.getTime());
        return questionDao.save(question);
    }

    @Override
    public Long addOptQuestion(QuestionOptAddDto questionOptAddDto) {

        Long id = addQuestion(questionOptAddDto);
        System.out.println(id);
        for (OptionDto optionDto: questionOptAddDto.getOptions()) {
            Option option=new Option();
            option.setIs_correct(optionDto.getIs_correct());
            option.setImage(optionDto.getImage());
            option.setQuest_id(id);
            option.setText(optionDto.getText());
            optionDao.addOption(option);
        }
        return id;
    }

    @Override
    public Long addSeqOptQuestion(QuestionSeqAddDto questionSeqAddDto) {
        Long id = addQuestion(questionSeqAddDto);
        System.out.println(id);
        for (SeqOptionDto seqOptionDto: questionSeqAddDto.getOptions()) {
            SeqOption seqOption=new SeqOption();
            seqOption.setImage(seqOptionDto.getImage());
            seqOption.setQuest_id(id);
            seqOption.setSerial_num(seqOptionDto.getSerial_num());
            seqOption.setText(seqOptionDto.getText());
            optionDao.addSeqOption(seqOption);
        }
        return id;
    }

    @Override
    public void deleteQuiz(Long id) {
        quizDao.delete(id);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionDao.delete(id);
    }

    @Override
    public List<Quiz> getUserQuizes(Long id) {
        return quizDao.getByUserId(id);
    }

    @Override
    public List<Quiz> getApprovedQuizes() {
        return quizDao.getApproved();
    }

    @Override
    public List<Quiz> getApprovedUserQuizes(Long user_id) {
        return quizDao.getApprovedForUser(user_id);
    }

    @Override
    public List<Quiz> getUserFavoritesQuizes(Long user_id) {
        return quizDao.getFavoriteQuizes(user_id);
    }

    @Override
    public List<Quiz> getSuggestion(Long user_id) {
        return quizDao.getSuggestion(user_id);
    }

    @Override
    public List<Quiz> getCompletedQuizes(Long user_id) {
        return quizDao.getCompletedQuizes(user_id);
    }

    @Override
    public List<Quiz> getCategoryQuizes(String category) {
        return quizDao.getCategoryQuizes(category);
    }

    @Override
    public List<Quiz> searchQuizes(String[] categories, String searchstring, String dateFrom, String dateTo, String user) {
        return quizDao.searchQuizes(categories,searchstring, dateFrom, dateTo, user);
    }


	
    @Override
    public List<Question> getQuizQuestion(Long id) {
        return questionDao.getQuizQusetions(id);
    }

    @Override
    public Question getQuestion(Long id) {
        return questionDao.getQuestion(id);
    }


    @Override
    public Long addQuiz(QuizAddDto quizAddDto) {
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        Long user_id=(long)quizAddDto.getUser_id();
        String quiz_name = quizAddDto.getTitle();
        Quiz quiz = new Quiz();
        quiz.setTitle(quiz_name);
        quiz.setDescription(quizAddDto.getDescription());
        quiz.setDate(date);
        quiz.setImage(quizAddDto.getImage());
        quiz.setUser_id(user_id);
        quiz.setStatus(new QuizStatus( 1L,"created"));

        Long quiz_id= quizDao.save(quiz);

        for (Long cat_id:quizAddDto.getCategories()) {
            quizCategoryDao.addQuizToCategory(quiz_id,cat_id);
        }

        UserActivity userActivity=new UserActivity();
        userActivity.setCategoryId(2L);
        userActivity.setDate(new java.sql.Timestamp(millis));
        userActivity.setUserId(user_id);
        userActivity.setElem_id(quiz.getId());
        userActivity.setText(String.format("%s created quiz named \"%s\"",userDao.get(user_id).getUsername(),quiz_name));
        userActivityDao.create(userActivity);
        return quiz_id;
    }


    @Override
    public List<Quiz> getAllQuizes() {
        List<Quiz> list =  quizDao.getAll();
        System.out.println(list.size());
        return list;
    }

    @Override
    public Quiz getQuiz(Long id) {

        return quizDao.get(id);
    }

    @Override
    public void approveQuiz(Quiz quiz) {
        Notification notification = new Notification();
        notification.setCategoryId(NOTIFICATION_APPROVED);
        notification.setUserId(getUserIdByQuiz(quiz.getId()));
        String[] params = new String[]{getTitle(quiz.getId())};
        if(quiz.getStatus().getName().equals("approved")) {
            quizDao.approve(quiz.getId());
            notification.setText(messageSource.
                    getMessage("quiz.approved", params,
                            userService.getUserLanguage(getUserIdByQuiz(quiz.getId()))));
        } else {
            notification.setText(quiz.getDescription());
            quizDao.delete(quiz.getId());
        }
        notificationService.create(notification);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Quiz> getCreated() {
        return this.quizDao.getCreated();
    }

    @Override
    public List<SessionStatsDto> getTopStats(Long quizId) {
        return quizDao.getTopStats(quizId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Long getUserIdByQuiz(Long quizId) {
        return quizDao.getUserIdByQuiz(quizId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String getTitle(Long quizId) {
        return quizDao.getTitle(quizId);
    }


}
