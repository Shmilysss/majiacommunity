package shequ.wqy.community.mapper;

import shequ.wqy.community.dto.QuestionQueryDTO;
import shequ.wqy.community.model.Question;

import java.util.List;

public interface QuestionExMapper {
    int updateView(Question record);

    int incCommentCount(Question question);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}