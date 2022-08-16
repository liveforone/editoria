package editoria.editoria.service;

import editoria.editoria.domain.Board;
import editoria.editoria.dto.BoardDto;
import editoria.editoria.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //== 작품의 전체 게시글 가져오기 ==//
    @Transactional(readOnly = true)
    public Page<Board> getBoardList(String book, Pageable pageable) {
        return boardRepository.findByBook(book, pageable);
    }

    //== 파일 있는 게시글 저장 ==//
    @Transactional
    public void saveBoardWithFile(
            MultipartFile uploadFile,
            String book,
            BoardDto boardDto,
            String author
    ) throws IOException {
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + "_" + uploadFile.getOriginalFilename();
        uploadFile.transferTo(new File(saveFileName));

        boardDto.setBook(book);
        boardDto.setSaveFileName(saveFileName);
        boardDto.setAuthor(author);
        boardRepository.save(boardDto.toEntity());
    }

    //== 파일 없는 게시글 저장 ==//
    @Transactional
    public void saveBoardWithNoFile(
            BoardDto boardDto,
            String author,
            String book
    ) {
        boardDto.setAuthor(author);
        boardDto.setBook(book);
        boardRepository.save(boardDto.toEntity());
    }

    //== 게시글 하나 불러오기 ==//
    @Transactional(readOnly = true)
    public Board getPost(Long id) {
        return boardRepository.findOneById(id);
    }

    //== 좋아요 업데이트 ==//
    @Transactional
    public void updateGood(Long id) {
        boardRepository.updateGood(id);
    }

    //== 게시글 파일 변경하며 수정 ==//
    @Transactional
    public void updateBoardWithFile(
            Long id,
            MultipartFile uploadFile,
            BoardDto boardDto
    ) throws IOException {
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + "_" + uploadFile.getOriginalFilename();
        boardDto.setId(id);  //id값 넣어야 업데이트 가능, id없으면 새로 생성해버림
        boardDto.setSaveFileName(saveFileName);
        uploadFile.transferTo(new File(saveFileName));
        boardRepository.save(boardDto.toEntity());
    }

    //== 게시글 기존 파일 유지하며 수정 ==//
    @Transactional
    public void updateBoardWithSaveFileName(
            Long id,
            BoardDto boardDto,
            String saveFileName
    ) {
        boardDto.setId(id);
        boardDto.setSaveFileName(saveFileName);
        boardRepository.save(boardDto.toEntity());
    }

    //== 파일없는 게시글 파일 없이 수정 ==//
    @Transactional
    public void updateBoardWithNoFile(Long id, BoardDto boardDto) {
        Board board = boardRepository.findOneById(id);
        boardDto.setId(id);
        boardDto.setSaveFileName(board.getSaveFileName());
        boardRepository.save(boardDto.toEntity());
    }

    //== 게시글 삭제 ==//
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
