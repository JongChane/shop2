package dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import logic.Exchange;

public interface ExchangeMapper {
	@Insert("insert into exchange (code, name, primeamt, sellamt, buyamt, edate) values"
	        + " (#{code}, #{name}, #{primeamt},"
	        + " #{sellamt}, #{buyamt}, #{edate})")
	void commlist(Exchange exchange);
	
	@Select("select ifnull(max(eno),0) from exchange")
	int maxEno();

}
