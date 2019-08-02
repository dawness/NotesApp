package com.levelsbeyond.NotesApp.repositories;

import com.levelsbeyond.NotesApp.models.Note;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NotesRepository {

    private final NamedParameterJdbcTemplate template;

    public NotesRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
        template.getJdbcTemplate().execute("CREATE SEQUENCE Note_Seq");
    }

    /**
     * Save a note to db
     * @param note
     */

    public void saveNotes(Note note) {
        String query = "INSERT INTO NOTE_ENTITY(id,body) values ( :id,:body)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("body", note.getBody());
        params.addValue("id", note.getId());
        template.update(query, params);
    }

    /**
     * Get next sequence number for id
     * @return Integer
     */

    public Integer getNextSequenceNumber() {

        return template.queryForObject(
                "select NOTE_SEQ.NEXTVAL from dual",
                new MapSqlParameterSource(),
                Integer.class
        );
    }

    /**
     * Get all notes from db
     * @return List<Note>
     */

    public List<Note> getAllNotes() {
        return this.template.query(
                "select * from Note_Entity",
                new NoteRowMapper());

    }

    /**
     * Get a note from db by Id
     * @param id
     * @return List<Note>
     */
    public List<Note> getNoteById(Integer id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return this.template.query(
                "select * from Note_Entity where id=:id",
                mapSqlParameterSource,
                new NoteRowMapper());
    }

    /**
     * Get all notes that contains provided string
     * @param body
     * @return List<Note>
     */
    public List<Note> getNotesByBody(String body) {
        List<Note> notes = getAllNotes();
        List<Note> newNotes = notes.stream().filter(n -> DoesItContainTheWord(n, body).equals(Boolean.TRUE))
                .collect(Collectors.toList());
        return newNotes;
    }

    /**
     * Check if that Note contains the word
     * @param note
     * @param body
     * @return Boolean
     */

    public Boolean DoesItContainTheWord(Note note, String body) {
        String[] words = note.getBody().split(" ");
        for (String word : words) {
            if (word.equalsIgnoreCase(body)) {
                return true;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Update a note by Id
     * @param note
     * @return int
     */

    public int updateNoteByid(Note note) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", note.getId());
        mapSqlParameterSource.addValue("body", note.getBody());
        return template.update(
                "update NOTE_ENTITY set body = :body where id =:id",
                mapSqlParameterSource

        );
    }

    /**
     * Delete a note by id
     * @param id
     * @return int
     */

    public int deleteNoteById(Integer id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return template.update("delete from NOTE_ENTITY where id = :id",
                mapSqlParameterSource);
    }
}

class NoteRowMapper implements RowMapper<Note> {
    @Override
    public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Note.builder()
                .id(rs.getInt("id"))
                .body(rs.getString("body"))
                .build();
    }
}