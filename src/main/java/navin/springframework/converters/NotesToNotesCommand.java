package navin.springframework.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import navin.springframework.commands.NotesCommand;
import navin.springframework.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if(source == null)
            return null;

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipieNotes(source.getRecipieNotes());

        return notesCommand;
    }
}
