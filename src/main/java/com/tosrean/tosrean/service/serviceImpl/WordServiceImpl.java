package com.tosrean.tosrean.service.serviceImpl;

import com.tosrean.tosrean.dto.KoreanAndKhmerDTO;
import com.tosrean.tosrean.dto.WordDTO;
import com.tosrean.tosrean.dto.WorkAndEnglishAndKhDTO;
import com.tosrean.tosrean.entity.KoreanAndKhmer;
import com.tosrean.tosrean.entity.Word;
import com.tosrean.tosrean.repository.KoreaAndKhmerRepository;
import com.tosrean.tosrean.repository.WordRepository;
import com.tosrean.tosrean.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    private final KoreaAndKhmerRepository wikipediaRepository;

    private final List<WorkAndEnglishAndKhDTO> listOfWords;

    @Override
    public Map<Integer, String> postWord(MultipartFile file) {
        Map<Integer, String> errMessage = new HashMap<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet =  workbook.getSheet("words");
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();
            Integer romNumber = 0;
            try {
                while (rowIterator.hasNext()) {

                    WordDTO wordDTO = new WordDTO();

                    Row row = rowIterator.next();
                    int cellIndex = 0;

                    Cell cellNo = row.getCell(cellIndex++);
                    romNumber = (int) cellNo.getNumericCellValue();

                    Cell celWordKorea = row.getCell(cellIndex++);
                    wordDTO.setWordKoren(celWordKorea.getStringCellValue());

                    Cell cellPartOfSpeech = row.getCell(cellIndex++);
                    wordDTO.setPartOfSpeech(cellPartOfSpeech.getStringCellValue());

                    Cell cellWordKhmer = row.getCell(cellIndex++);
                    wordDTO.setTranslateKhmer(cellWordKhmer.getStringCellValue());

                    Cell cellWordEnglish = row.getCell(cellIndex++);
                    wordDTO.setTranslateEnglish(cellWordEnglish.getStringCellValue());
                    Word word = new Word(wordDTO);

                    log.info("Korean :: {}", wordDTO.getWordKoren());
                }
            } catch (Exception e) {
                errMessage.put(romNumber, e.getMessage());
            }
        } catch (Exception e) {
            log.info("Error :: {}", e.getMessage());
        }
        return errMessage;
    }

    @Override
    public void postPFD(MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String str = pdfStripper.getText(document);
            BufferedReader reader = new BufferedReader(new StringReader(str));
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line
//                System.out.println(line);
//                log.info("Result :: {}", line);
                textSplitterToLine(line);
            }
            reader.close();

            for (WorkAndEnglishAndKhDTO waeak: listOfWords) {
                log.info("List of Korean and English :: {}", waeak);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postKoreanKhmer(MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet =  workbook.getSheet("koreanAndKhmer");
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {

                KoreanAndKhmerDTO koreanAndKhmerDTO = new KoreanAndKhmerDTO();

                Row row = rowIterator.next();
                int cellIndex = 0;

                Cell celWordKorea = row.getCell(cellIndex++);
                koreanAndKhmerDTO.setWordKoren(celWordKorea.getStringCellValue());

                Cell cellWordKhmer = row.getCell(cellIndex++);
                koreanAndKhmerDTO.setWordKhmer(cellWordKhmer.getStringCellValue());
                KoreanAndKhmer koreanAndKhmer = new KoreanAndKhmer(koreanAndKhmerDTO);
                wikipediaRepository.save(koreanAndKhmer);

                log.info("korean and khmer :: {}", koreanAndKhmerDTO);
            }
        } catch (Exception e) {

        }
    }

    private void textSplitterToLine (String str) {

        // Define the regular expression pattern to find numbers
        String numberPattern = "\\d+";

        // Split the text based on the pattern
        String[] parts = str.split("(?=" + numberPattern + ")");

        // Print each part on a new line
        for (String part : parts) {
//            lines.add(part.trim());
            String english = removeSpecialCharacters(removeNumKoreanFormText(part.trim()));
            String korean = removeSpecialCharacters(removeNumEngFormText(part.trim()));

            if (!english.isBlank() && !korean.isBlank()) {
                WorkAndEnglishAndKhDTO kdto = WorkAndEnglishAndKhDTO.builder()
                        .workEnglish(english)
                        .wordKoren(korean)
                        .wordKhmer("")
                        .build();
                listOfWords.add(kdto);
            }

        }

    }

    private String removeNumKoreanFormText(String str) {
        // Define a regular expression pattern to match Korean characters and digits
        String pattern = "[\\uAC00-\\uD7AF\\d]+";

        // Replace Korean characters and digits with an empty string
        String result = str.replaceAll(pattern, "").replaceAll("\\s{2,}", " ").trim();

        // return the result
        return  result;
    }

    private String removeNumEngFormText(String str) {

        // Define a regular expression pattern to match English characters and digits
        String pattern = "[a-zA-Z\\d]+";

        // Replace English characters and digits with an empty string
        String result = str.replaceAll(pattern, "").replaceAll("\\s{2,}", " ").trim();

        // return the result
        return  result;
    }

    private String removeSpecialCharacters (String str) {
        // Define a regular expression pattern to match special characters
        String pattern = "[^a-zA-Z0-9가-힣\\s]";

        // Replace special characters with an empty string
        String result = str.replaceAll(pattern, "").replaceAll("\\s{2,}", " ").trim();

        // return the result
        return  result;
    }
}
