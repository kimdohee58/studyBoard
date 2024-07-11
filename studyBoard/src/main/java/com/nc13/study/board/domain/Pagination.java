package com.nc13.study.board.domain;

import lombok.Data;

@Data
public class Pagination {
    // 페이지당 보여지는 게시글 최대 수
    private int pageSize = 10;

    // 페이징된 버튼의 블럭당 최대 개수
    private int blockSize = 5;

    // 현재페이지
    private int page = 1;

    // 현재 블럭
    private int block = 1;

    // 총 게시글 수
    private int totalBoard;

    // 총 블럭 수
    private int totalBlock;

    // 블럭 시작 페이지
    private int startPage = 1;

    // 블럭 마지막 페이지
    private int endPage = 1;

    // db 접근 시작 index
    private int startIndex = 0;

    // 이전 블럭의 마지막 페이지
    private int prevBlock;

    // 다음 블럭의 시작 페이지
    private int nextBlock;


    public Pagination(int totalBoard, int page) {
        // 총 게시물 수와 현재 페이지를 Controller에서 받아온다

        setPage(page); // 현재 페이지
        setTotalBoard(totalBoard); // 총 게시글 수

        // 총 페이지 수, 한 페이지의 최대 개수를 총 게시글 수 * 1.0으로 나누고 올림해준다.
        setTotalBoard((int) Math.ceil(totalBoard * 1.0 / pageSize));

        // 총 블럭 수
        setTotalBlock((int) Math.ceil(totalBoard * 1.0 / blockSize));

        // 현재 블럭, 현재 페이지*1.0을 블록의 최대 개수로 나누고 올림
        setBlock((int) Math.ceil((page * 10) / blockSize));

        // 블럭 시작 페이지
        setStartPage((block - 1) * blockSize + 1);

        // 블럭 마지막 페이지에 대한 validation
        if (endPage > totalBoard) {
            this.endPage = totalBoard;
        }

        // < 블럭(클릭 시, 이전 블럭 마지막 페이지로 이동
        setPrevBlock((block * blockSize) - blockSize);

        // 이전 블럭에 대한 validation
        if (prevBlock < 1) {
            this.prevBlock = 1;
        }

        // > 블럭 (클릭 시, 다음 블럭 첫번째 페이지
        setNextBlock((block * blockSize) + 1);

        // 다음 블럭에 대한 validation
        if (nextBlock > totalBoard) {
            nextBlock = totalBoard;
        }

        // 다음 블럭에 대한 validation
        if (nextBlock > totalBoard) {
            nextBlock = totalBoard;
        }

        // DB 접근 시작 index
        setStartIndex((page - 1) * pageSize);
    }
}
