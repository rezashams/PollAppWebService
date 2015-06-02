package com.pollApp.service;

import java.util.Comparator;

import com.pollApp.model.Poll;

public class SortPollByDate implements Comparator {
	public int compare(Object f, Object s) {
        Poll poll1 = (Poll) f;
        Poll poll2 = (Poll) s;
        if (poll1.getCreationDate()> poll2.getCreationDate() )
            return -1;
        if (poll1.getCreationDate() == poll2.getCreationDate())
            return 0;
        return 1;
    }

}
