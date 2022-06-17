package com.example.tedtalks.services;

import com.example.tedtalks.models.TedTalkCsv;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final TedTalkService tedTalkService;

    public void uploadFile(MultipartFile file, Model model) {
        if (validateFile(file, model)) {
            try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean itemCsv = getCsvBean(reader);

                List<TedTalkCsv> itemCsvList = itemCsv.parse();

                tedTalkService.saveAllTedTalks(itemCsvList);
                setSuccessStatus(model);
            } catch (Exception ex) {
                setFailureStatus(model, ex);
            }
        }
    }

    private CsvToBean getCsvBean(Reader reader) {
        return new CsvToBeanBuilder(reader)
                .withType(TedTalkCsv.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withThrowExceptions(false)
                .withIgnoreQuotations(true)
                .withKeepCarriageReturn(false).build();
    }

    private boolean validateFile(MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
            return false;
        }

        return true;
    }

    private void setFailureStatus(Model model, Exception ex) {
        model.addAttribute("message", "An error occurred while processing the CSV file. " + ex.getMessage());
        model.addAttribute("status", false);
    }

    private void setSuccessStatus(Model model) {
        model.addAttribute("message", "Success");
        model.addAttribute("status", true);
    }
}
