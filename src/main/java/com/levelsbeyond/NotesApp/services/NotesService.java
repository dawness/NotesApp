package com.levelsbeyond.NotesApp.services;

import com.levelsbeyond.NotesApp.models.Note;
import com.levelsbeyond.NotesApp.repositories.NotesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotesService {
    private final NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Note addNote(Note note) {
        Integer nextSequenceNumber = notesRepository.getNextSequenceNumber();
        note = note.toBuilder()
                .id(nextSequenceNumber)
                .build();
        notesRepository.saveNotes(note);
        return note;
    }

    public List<Note> getNotes() {
        return notesRepository.getAllNotes();
    }

    public List<Note> getNoteByid(Integer id) {
        return notesRepository.getNoteById(id);
    }

    public List<Note> getNotesByBody(String body) {

        return notesRepository.getNotesByBody(body);
    }

    public int updateNoteById(Note note) {

        return notesRepository.updateNoteByid(note);
    }

    public int deleteNoteByid(Integer id) {

        return notesRepository.deleteNoteById(id);
    }
}
