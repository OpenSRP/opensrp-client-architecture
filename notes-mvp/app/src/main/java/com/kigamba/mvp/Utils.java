package com.kigamba.mvp;

import android.support.annotation.Nullable;

import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;

/**
 * Created by Kigamba on 01-May-18.
 */

public class Utils {

    public static Note prepareNoteForSaving(@Nullable Note note) {
        if (note == null) {
            note = new Note();
        } else {
            note.setLastModified(System.currentTimeMillis());
        }

        return note;
    }

    public static boolean validateNoteDetails(String title, String description) {
        if (!isBlank(title) && !isBlank(description)) {
            return true;
        }

        return false;
    }

    public static boolean isNoteNew(Note note) {
        return note.getId() == 0;
    }

    public static boolean saveNote(NoteDao noteDao, Note note) {
        if (Utils.isNoteNew(note)) {
            noteDao.insertAll(note);
            return true;
        } else {
            noteDao.update(note);
            return true;
        }
    }

    /**
     * <p>Checks if a (trimmed) String is <code>null</code> or empty.</p>
     *
     * @param str the String to check
     * @return <code>true</code> if the String is <code>null</code>, or
     *  length zero once trimmed
     */
    public static boolean isBlank( String str )
    {
        return ( ( str == null ) || ( str.trim().length() == 0 ) );
    }
}
