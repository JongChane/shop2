package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.SaleMapper;
import logic.Sale;

@Repository
public class SaleDao {
	@Autowired
	private SqlSessionTemplate template;
	Class<SaleMapper> cls = SaleMapper.class;

	public int getMaxSaleId() { //saleid 최대값 조회
		return template.getMapper(cls).maxid();
	}
	public void insert(Sale sale) { //sale 테이블에 데이터 추가
		template.getMapper(SaleMapper.class).insert(sale);		
	}
	public List<Sale> list(String userid) {
		return template.getMapper(SaleMapper.class).list(userid);
	}
}
