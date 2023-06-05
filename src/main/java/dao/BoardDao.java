package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dao.mapper.BoardMapper;
import logic.Board;

@Repository
public class BoardDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String,Object> param = new HashMap<>();
	private Class<BoardMapper> cls = BoardMapper.class;

	public int maxNum() {
		return template.getMapper(cls).maxNum();
	}
	public void insert(Board board) {
		template.getMapper(cls).insert(board);
	}
	public int count(String boardid, String searchtype, String searchcontent) {
		param.clear();
		param.put("boardid", boardid);
		param.put("searchtype",searchtype);
		param.put("searchcontent",searchcontent);
		return template.getMapper(cls).count(param);
	}
	public List<Board> list
	(Integer pageNum, int limit, String boardid, String searchtype, String searchcontent) {
		param.clear();
		param.put("startrow", (pageNum - 1) * limit); //1페이지 : 0, 2페이지 : 10 
		param.put("limit",  limit);		
		param.put("boardid",  boardid);		
		param.put("searchtype",searchtype);
		param.put("searchcontent",searchcontent);
		return template.getMapper(cls).select(param);
	}
	public Board selectOne(Integer num) {
		param.clear();
		param.put("num", num);
		return template.selectOne("dao.mapper.BoardMapper.select",param);
	}
	public void addReadcnt(Integer num) {
		param.clear();
		param.put("num", num);
		template.getMapper(cls).addReadcnt(param);
	}
	public void updateGrpStep(Board board) { //답변글 등록시 기존 게시물의 grpstep값을 +1 변경
		param.clear();
		param.put("grp", board.getGrp());         //원글의 grp
		param.put("grpstep", board.getGrpstep()); //원글의 grpstep
		template.getMapper(cls).updateGrpStep(param);
	}
	public void update(Board board) {
		template.getMapper(cls).update(board);		
	}
	public void delete(Integer num) {
		template.getMapper(cls).delete(num);
	}
	public List<Map<String, Object>> graph1(String id) {
		return template.getMapper(cls).graph1(id);
	}
}
