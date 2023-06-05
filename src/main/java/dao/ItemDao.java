package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.ItemMapper;
import logic.Item;

@Repository // @Component + dao 기능(데이터베이스연결)
public class ItemDao {
	@Autowired
	private SqlSessionTemplate template; //org.mybatis.spring.SqlSessionTemplate 객체 주입
	private Map<String, Object> param = new HashMap<>();
	private final Class<ItemMapper> cls = ItemMapper.class;

	public List<Item> list() {
		return template.getMapper(cls).select(null); //item 테이블의 전체 내용을 Item 객체의 목록 리턴 
	}

	public Item getItem(Integer id) {
		param.clear();
		param.put("id", id);
		//item 테이블의 id값의 해당하는 내용을 Item 객체의 목록 리턴. 한건만 필요
//		return template.getMapper(cls).select(param).get(0);  
		//selectOne() : 리턴되는 데이터의 레코드 갯수가 한개인 경우
		return template.selectOne("dao.mapper.ItemMapper.select", param); 
	}

	public int maxId() {
		return template.getMapper(cls).maxId();
	}

	public void insert(Item item) {
		template.getMapper(cls).insert(item);
	}

	public void update(Item item) {
		template.getMapper(cls).update(item);
	}

	public void delete(Integer id) {
		param.clear();
		param.put("id", id);
		template.getMapper(cls).delete(param);
	}
}
