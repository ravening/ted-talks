package com.example.tedtalks.models;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TedTalkCsv {

    @CsvBindByName(column = "title")
    String title;

    @CsvBindByName(column = "author")
    String author;

    @CsvBindByName(column = "date")
    String date;

    @CsvBindByName(column = "views")
    long views;

    @CsvBindByName(column = "likes")
    long likes;

    @CsvBindByName(column = "link")
    String link;
}
