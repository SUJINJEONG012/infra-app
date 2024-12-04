package board.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PagingService {

	// 페이지에 보여줄 데이터를 
	// 한 페이지당 보여줄 페이지의 개수 : 예 10개 , 5개
	// 시작과 끝페이지의 개수
	private final static int PAGE_LENGTH = 10;
	
	
	// 한 화면에 10개씩 보여줄건데, 몇번부터 몇번까지 출력해줄건가!
	// start, end 페이지 
	public List<Integer> getPagingNumber(int pageNumber, int totalPages) {
		int startPage = Math.max((pageNumber - (PAGE_LENGTH /2)), 0);
		int endPage = Math.min(startPage + PAGE_LENGTH, totalPages);
		return IntStream.range(startPage, endPage).boxed().toList();
	}
	
	public int getPageLength() {	
		return PAGE_LENGTH;
	}

}
