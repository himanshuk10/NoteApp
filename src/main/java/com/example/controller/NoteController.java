package com.example.controller;





import com.example.dto.NoteRequest;
import com.example.dto.NoteResponse;
import com.example.entity.Note;
import com.example.entity.User;
import com.example.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody NoteRequest request,
                                        Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).body(
                    java.util.Map.of("error", "Unauthorized")
            );
        }

        User currentUser = (User) authentication.getPrincipal();
        Note savedNote = noteService.createNote(currentUser, request);

        NoteResponse response = new NoteResponse(
                savedNote.getId(),
                savedNote.getTitle(),
                savedNote.getContent(),
                savedNote.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // -----------------------------------
    // List Notes (Protected)
    // -----------------------------------
    @GetMapping
    public ResponseEntity<?> getNotes(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).body(
                    java.util.Map.of("error", "Unauthorized")
            );
        }

        User currentUser = (User) authentication.getPrincipal();
        List<Note> notes = noteService.getNotesByUser(currentUser);

        List<NoteResponse> response = notes.stream()
                .map(note -> new NoteResponse(
                        note.getId(),
                        note.getTitle(),
                        note.getContent(),
                        note.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
