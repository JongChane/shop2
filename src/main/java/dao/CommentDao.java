package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.BoardMapper;
import dao.mapper.CommentMapper;
import logic.Comment;
@Repository
public class CommentDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String,Object> param = new HashMap<>();
	private Class<CommentMapper> cls = CommentMapper.class;
	
	public void insertComment(Comment com) {
		template.getMapper(cls).insert(com);
		
	}

	public List<Comment> commlist(Integer num) {
		param.clear();
		param.put("num", num);
		return template.getMapper(cls).commlist(num);
		
	}

	public int maxseq(int num) {
		return template.getMapper(cls).maxseq(num);
	}

	public void delete(int num, int seq) {
		template.getMapper(cls).delete(num,seq);
		
	}

	public Comment selectPass(int num, int seq) {
		return template.getMapper(cls).selectPass(num, seq);
	}

}
