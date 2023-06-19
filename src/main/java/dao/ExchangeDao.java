package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.CommentMapper;
import dao.mapper.ExchangeMapper;
import logic.Exchange;

@Repository
public class ExchangeDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String,Object> param = new HashMap<>();
	private Class<ExchangeMapper> cls = ExchangeMapper.class;
	
	public void insert(Exchange exchange) {
		System.out.println(exchange);
		template.getMapper(cls).commlist(exchange);	
	}
}
