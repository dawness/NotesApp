package com.levelsbeyond.NotesApp.controllers;

import com.levelsbeyond.NotesApp.models.Note;
import com.levelsbeyond.NotesApp.services.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    /**
     * Add a new note and returns the status
     *
     * @param note
     * @return ResponseEntity<Note>
     */

    @PostMapping("/notes")
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note noteWithId = notesService.addNote(note);
        return new ResponseEntity<Note>(noteWithId, HttpStatus.OK);
    }

    /**
     * Returns a note by Id
     *
     * @param id
     * @return ResponseEntity
     */

    @GetMapping("/notes/{id}")
    public ResponseEntity getANoteById(@PathVariable Integer id) {
        List<Note> notes = notesService.getNoteByid(id);

        if (notes.size() > 0)
            return new ResponseEntity<>(notes.get(0), HttpStatus.OK);
        else
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No Note in that ID");
    }

    /**
     * Return all the notes
     * Or Return all the notes that contain the provided word
     *
     * @param body
     * @return ResponseEntity
     */

    @GetMapping("/notes")
    public ResponseEntity getNotes(@RequestParam(required = false) String body) {
        List<Note> notes;

        if (StringUtils.isEmpty(body)) {
            notes = notesService.getNotes();

        } else {
            notes = notesService.getNotesByBody(body);
        }
        if (notes.size() > 0) {
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Notes Not found");
        }

    }

    /**
     * Update a note by id
     *
     * @param id
     * @param note
     * @return ResponseEntity
     */

    @PutMapping("/notes/{id}")
    public ResponseEntity updateNote(@PathVariable Integer id, @RequestBody Note note) {
        note = note.toBuilder().id(id).build();
        int success = notesService.updateNoteById(note);
        if (success > 0) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Note in that Id");
        }
    }

    /**
     * Delete a note by Id
     *
     * @param id
     * @return
     */

    @DeleteMapping("/notes/{id}")
    public ResponseEntity deleteNote(@PathVariable Integer id) {
        int success = notesService.deleteNoteByid(id);
        if (success > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Note with id " + id + " is Deleted");

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Note in that Id");
        }

    }

}



