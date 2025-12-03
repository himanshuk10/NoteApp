package com.example.service;


import com.example.dto.NoteRequest;
import com.example.entity.Note;
import com.example.entity.User;
import com.example.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note createNote(User user, NoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setCreatedAt(LocalDateTime.now());
        note.setUser(user);

        return noteRepository.save(note);
    }

    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUser(user);
    }
}