package com.example.tedtalks.services;

import com.example.tedtalks.models.TedTalk;
import com.example.tedtalks.models.TedTalkCsv;
import com.example.tedtalks.repository.TedTalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TedTalkService {

    private final TedTalkRepository tedTalkRepository;

    public void saveAllTedTalks(List<TedTalkCsv> tedTalkCsvs) {
        List<TedTalk> tedTalks = tedTalkCsvs
                .stream()
                .map(tedTalkCsv -> csvToBeanFunction().apply(tedTalkCsv))
                .collect(Collectors.toList());

        saveAll(tedTalks);
    }
    public void saveAll(List<TedTalk> tedTalks) {
        this.tedTalkRepository.saveAll(tedTalks);
    }

    public TedTalk saveTedTalk(TedTalk tedTalk) {
        return this.tedTalkRepository.save(tedTalk);
    }

    public List<TedTalk> getAll() {
        return tedTalkRepository.findAll();
    }

    @Cacheable(cacheNames = "talks", key = "#author")
    public List<TedTalk> getTalksByAuthor(String author) {
        return tedTalkRepository.findByAuthorContainsIgnoreCase(author);
    }

    @Cacheable(cacheNames = "talks", key = "#title")
    public List<TedTalk> getTalksByTitle(String title) {
        return tedTalkRepository.findByTitleContainsIgnoreCase(title);
    }

    @Cacheable(cacheNames = "talks", key = "#views")
    public List<TedTalk> getTalksByViewsCount(Long views) {
        return tedTalkRepository.findByViews(views);
    }

    @Cacheable(cacheNames = "talks", key = "#likes")
    public List<TedTalk> getTalksByLikesCount(Long likes) {
        return tedTalkRepository.findByLikes(likes);
    }

    @CachePut(cacheNames = "talks", key = "#tedTalk.id")
    public TedTalk updateTedTalk(Long id, TedTalk tedTalk) {
        checkIfEntityExists(id);

        return tedTalkRepository.save(tedTalk);
    }

    @CacheEvict(cacheNames = {"talks"}, key = "#id", value = "talks")
    public void deleteTedTalk(Long id) {
        checkIfEntityExists(id);

        tedTalkRepository.deleteById(id);
    }

    private void checkIfEntityExists(Long id) {
        Optional<TedTalk> tedTalkOptional = tedTalkRepository.findById(id);

        if (tedTalkOptional.isEmpty()) {
            throw new EntityNotFoundException("Unable to find ted talk with id " + id);
        }
    }

    Function<TedTalkCsv, TedTalk> csvToBeanFunction() {
        return tedTalkCsv -> TedTalk.builder()
                .title(tedTalkCsv.getTitle())
                .author(tedTalkCsv.getAuthor())
                .date(tedTalkCsv.getDate())
                .views(tedTalkCsv.getViews())
                .likes(tedTalkCsv.getLikes())
                .link(tedTalkCsv.getLink()).build();
    }
}
