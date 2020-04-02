package shequ.wqy.community.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shequ.wqy.community.dto.PaginationDTO;
import shequ.wqy.community.dto.QuestionDTO;
import shequ.wqy.community.exception.CustomizeErrorCode;
import shequ.wqy.community.exception.CustomizeException;
import shequ.wqy.community.mapper.QuestionExMapper;
import shequ.wqy.community.mapper.QuestionMapper;
import shequ.wqy.community.mapper.UserMapper;
import shequ.wqy.community.model.Question;
import shequ.wqy.community.model.QuestionExample;
import shequ.wqy.community.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExMapper questionExMapper;

    public PaginationDTO list(Integer page, Integer size) {

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        Integer totalPage = new Integer(0);
        ;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalPage, page, size);

        return paginationDTO;
    }

    public PaginationDTO listByUserId(Long userId, Integer page, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        Integer totalPage = new Integer(0);
        ;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = size * (page - 1);
//        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample1, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalPage, page, size);

        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {//新增
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setViewCount(0);
            questionMapper.insert(question);
        } else {//更新
            question.setGmtModified(question.getGmtCreate());
            Question updQuestion = new Question();
            updQuestion.setGmtModified(System.currentTimeMillis());
            updQuestion.setTitle(question.getTitle());
            updQuestion.setDescription(question.getDescription());
            updQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int upd = questionMapper.updateByExampleSelective(updQuestion, example);
            if(upd != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void updView(Long id){
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExMapper.updateView(question);

    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String tags = questionDTO.getTag();
        String tagsL = tags.replace(',','|');
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(tagsL);
        List<Question> questions = questionExMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(q,dto);
            return dto;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
