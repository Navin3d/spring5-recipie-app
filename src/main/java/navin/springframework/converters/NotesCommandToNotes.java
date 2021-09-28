package navin.springframework.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import navin.springframework.commands.NotesCommand;
import navin.springframework.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if(source == null)
            return null;

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipieNotes(source.getRecipieNotes());

        return notes;
    }
}
