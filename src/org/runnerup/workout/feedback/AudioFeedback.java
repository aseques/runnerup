/*
 * Copyright (C) 2012 jonas.oreland@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.runnerup.workout.feedback;

import org.runnerup.workout.Dimension;
import org.runnerup.workout.Event;
import org.runnerup.workout.Feedback;
import org.runnerup.workout.Scope;
import org.runnerup.workout.Workout;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class AudioFeedback extends Feedback {

	Event event = Event.STARTED;
	Scope scope = Scope.WORKOUT;
	Dimension dimension = Dimension.DISTANCE;

	public AudioFeedback(Scope scope, Event event) {
		super();
		this.scope = scope;
		this.event = event;
		this.dimension = null;
	}

	public AudioFeedback(Scope scope, Dimension dimension) {
		super();
		this.scope = scope;
		this.event = null;
		this.dimension = dimension;
	}

	@Override
	public boolean equals(Feedback _other) {
		if (!(_other instanceof AudioFeedback))
			return false;

		AudioFeedback other = (AudioFeedback) _other;
		if (this.scope != other.scope)
			return false;

		if (this.event != other.event)
			return false;

		if (this.dimension != other.dimension)
			return false;

		return true;
	}

	@Override
	public void emit(Workout w, Context ctx) {
		String msg = null;
		if (event != null) {
			msg = scope.getCue(ctx) + " " + event.getCue(ctx);
		} else {
			double val = w.get(scope, dimension); // SI
			msg = scope.getCue(ctx) + " " + dimension.getCue(ctx, val);
		}
		w.getTts().speak(msg, TextToSpeech.QUEUE_ADD, null);
	}
}
