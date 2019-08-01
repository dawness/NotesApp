package com.levelsbeyond.NotesApp.controllers;

import com.levelsbeyond.NotesApp.models.Note;
import com.levelsbeyond.NotesApp.services.NotesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class
)
public class NotesControllerTest {

    @InjectMocks
    private NotesController notesController;
    @Mock
    private NotesService notesService;


    @Test
    public void itReturnsOk_andSavesNotes() throws Exception {
        Note note = Note.builder()
                .body("First Note")
                .build();

        when(notesService.addNote(note)).thenReturn(note.toBuilder().id(1).build());
        ResponseEntity<Note> newNote = notesController.addNote(note);
        assert (note.getBody().equals(newNote.getBody().getBody()));
        assertEquals(newNote.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getANoteById_with_validId() {
        Integer id = 1;
        when(notesService.getNoteByid(id)).thenReturn(Collections.singletonList(Note.builder().id(1).body("return the call").build()));
        ResponseEntity<Note> newNote = notesController.getANoteById(id);
        assertEquals(newNote.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getANoteById_with_invalidId() {
        Integer id = 5;
        when(notesService.getNoteByid(id)).thenReturn(Collections.EMPTY_LIST);
        ResponseEntity<Note> newNote = notesController.getANoteById(id);
        assertEquals(newNote.getStatusCode(), HttpStatus.NOT_FOUND);
    }



    @Test
    public void getNotes_whenThereAreNotes() {
        when(notesService.getNotes()).thenReturn(Collections.singletonList(Note.builder().id(1).body("return the call").build()));
        ResponseEntity<List<Note>> notes = notesController.getNotes(null);
        assertEquals(notes.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getNotes_whenThereAreNoNotes() {
        when(notesService.getNotes()).thenReturn(Collections.EMPTY_LIST);
        ResponseEntity<List<Note>> notes = notesController.getNotes(null);
        assertEquals(notes.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getNotes_usingASearchString() {
        List<Note> notes = Arrays.asList(Note.builder().id(1).body("First Note").build(),
                Note.builder().id(2).body("Second Note").build());

        when(notesService.getNotesByBody("Note")).thenReturn(notes);
        ResponseEntity<List<Note>> newNotes = notesController.getNotes("Note");
        assertEquals(newNotes.getBody().size(), 2);
    }
}
