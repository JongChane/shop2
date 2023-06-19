package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import logic.Exchange;
import logic.ShopService;

public class CountScheduler {
	private int cnt;
	@Autowired
	ShopService service;
	/*
	 * cron 
	 * 	 1. 특정시간, 주기적으로 프로그램을 수행하는 프로세스. 유닉스 기반 프로세스.
	 * 	 2. 리눅스에서 crontab 명령 설정 가능함.
	 * 	 3. 스프링에서는 cron 기능을 Scheduler라 한다.
	 * 
	 * 	 크론식 : 0/5 * * * * ? => 5초마다 실행 
	 * 		cron을 설정할 수 있는 형식
	 * 			
	 * 	  형식 :  초 분 시 일 월 요일 [년도]
	 * 	    초 : 0 ~ 59
	 * 		분 : 0 ~ 59
	 * 		시 : 0 ~ 23
	 * 		일 : 1 ~ 31
	 * 		월 : 1 ~ 12 (JAN, FEB, MAR, APR, ... , DEC)
	 *     요일 : 1 ~ 7 (MON, TUE, WED...SUN)
	 *   표현방식 
	 *   	* 	: 매번
	 *    	0/5 : 주기마다 실행 0/5 => 0부터 5초 주기로 실행
	 *    	?	: 설정 없음 (일, 요일에서 사용됨)
	 *    예시
	 *    0/10 * * * * ?   : 10초마다 한 번씩
	 *    0 0/1 * * *?     : 1분마다 한 번씩
	 *    0 20,50 * * * ?  : 매시간 20, 50분마다 실행
	 *    0 0 0/3 * * ?    : 3시간마다 한 번씩 
	 *    0 0 12 ? * 1     : 월요일 12시에 실행 
	 *    0 0 12 ? * MON   : 월요일 12시에 실행 
	 *    0 0 10 ? * SAT,SUN   : 주말 10시에 실행 
	 *    
	 *    크론식 작성 사이트 : http://www.cronmaker.com
	 *     
	 */
//	@Scheduled(cron="0/5 * * * * ?")
	public void execute1 () {
		System.out.println("cnt : " + cnt++);
	}
//	@Scheduled(cron="0 10 15 14 6 ?") //자동 호출
	public void execute2 () {
		System.out.println("3시 8분입니다.");
	}
	/*
	 * 1. 평일 아침 10시에 환율 정보를 조회해서 DB에 등록 
	 * 		create table exchange (
			code varchar(10), #통화코드
			NAME VARCHAR(50), #통화명
			primeamt FLOAT,	#매매기준율
			sellamt FLOAT,		#매도율
			buyamt FLOAT		#매입율
			
 )
	 * 
	 */
	@Scheduled(cron="0 10 10 * * ?")
	public void exchangeinsert() {
		System.out.println("환율정보 등록 시작.");
		Document doc = null;
		List<List<String>> trlist = new ArrayList<>();  //미국,중국,일본,유로 통화들만 저장 목록
		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
		String exdate = null;
		try {
			doc = Jsoup.connect(url).get();
			Elements trs = doc.select("tr"); //tr 태그들 
			//p.table-unit : class속성의 값이 table-unit인 p태그
			exdate = doc.select("p.table-unit").html(); //조회기준일 : 2023.06.02
			for(Element tr : trs) {
				List<String> tdlist = new ArrayList<>();  //tr 태그의 td 태그 목록
				Elements tds = tr.select("td"); //tr 태그의 하위 td 태그들
				for(Element td : tds) {
					tdlist.add(td.html()); //
				}
			    if (tdlist.size() > 0) {
				   if(tdlist.get(0).equals("USD") //미달러 통화코드
			    	|| tdlist.get(0).equals("CNH") //중국 통화코드
			        || tdlist.get(0).equals("JPY(100)") //일본 통화코드
			        || tdlist.get(0).equals("EUR")) {   //유로 통화코드
				    trlist.add(tdlist);
				   }
			    }
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	
		service.addExcahnge(trlist,exdate);
	}
}
