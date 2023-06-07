package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class Sale {	
	private int saleid;
	private String userid;
	private Date saledate;
	private User user;
	private List<SaleItem> itemList = new ArrayList<>(); //주문상품목록
	public int getTotal() { //주문상품 전체 금액 리턴
		int sum = 0;
		 //  (상품가격*주문수량 )의 전체 가격의 합
/*		
		for(SaleItem s : itemList) {
			sum += s.getItem().getPrice() * s.getQuantity();
		}
		return sum;
*/
		return itemList.stream()
			 .mapToInt(s->s.getItem().getPrice() * s.getQuantity()).sum(); 
	}
}
