package app.controllers;

import app.configurations.NotesAppConfig;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Note;
import app.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private NotesAppConfig config;

    @GetMapping("/profile")
    public String getProfile() {
        return config.getProfile();
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) throws IncorrectBodyException {
        return repository.createNote(note);
    }

    @GetMapping("/{id}")
    public Note readNote(@PathVariable("id") Integer id) throws NoDataException {
        return repository.readNote(id);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable("id") Integer id, @RequestBody Note note) throws IncorrectBodyException{
        return repository.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") Integer id) throws NoDataException {
        repository.deleteNote(id);
    }
}
