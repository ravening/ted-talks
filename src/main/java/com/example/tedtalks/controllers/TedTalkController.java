package com.example.tedtalks.controllers;

import com.example.tedtalks.models.TedTalk;
import com.example.tedtalks.models.TedTalkCsv;
import com.example.tedtalks.services.TedTalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/talks")
@RequiredArgsConstructor
public class TedTalkController {

    private final TedTalkService tedTalkService;

    @Operation(summary = "Get all talks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found list of talks"),
            @ApiResponse(responseCode = "404", description = "No talks found")
    })
    @GetMapping(produces = { "application/json", "application/xml" })
    public ResponseEntity<List<TedTalk>> getAllTalks() {
        return new ResponseEntity<>(tedTalkService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/author/{name}")
    public ResponseEntity<List<TedTalk>> getTalksByAuthor(@PathVariable("name") String name) {
        return new ResponseEntity<>(tedTalkService.getTalksByAuthor(name), HttpStatus.OK);
    }

    @GetMapping("/title/{name}")
    public ResponseEntity<List<TedTalk>> getTalksByTitle(@PathVariable("name") String name) {
        return new ResponseEntity<>(tedTalkService.getTalksByTitle(name), HttpStatus.OK);
    }

    @GetMapping("/likes/{likes}")
    public ResponseEntity<List<TedTalk>> getTalksByLikes(@PathVariable("likes") Long likes) {
        return new ResponseEntity<>(tedTalkService.getTalksByLikesCount(likes), HttpStatus.OK);
    }

    @GetMapping("/views/{views}")
    public ResponseEntity<List<TedTalk>> getTalksByViews(@PathVariable("views") Long views) {
        return new ResponseEntity<>(tedTalkService.getTalksByViewsCount(views), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TedTalk> postNewTedTalk(@RequestBody TedTalk tedTalk) {
        return new ResponseEntity<>(tedTalkService.saveTedTalk(tedTalk), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        tedTalkService.deleteTedTalk(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TedTalk> update(@PathVariable("id") Long id, @RequestBody TedTalk tedTalk) {
        return new ResponseEntity<>(tedTalkService.updateTedTalk(id, tedTalk), HttpStatus.OK);
    }
}
