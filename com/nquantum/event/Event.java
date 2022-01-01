package com.nquantum.event;

import java.lang.reflect.InvocationTargetException;

import com.nquantum.Asyncware;

public abstract class Event {

	private boolean cancelled;

	public static enum State {
		PRE("PRE", 0), POST("POST", 1);
		private State(String string, int number) {
		}
	}

	public Event call() {
		this.cancelled = false;
		call(this);
		return this;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled) {

		this.cancelled = cancelled;
	}

	public State getStatePre(){
		return State.PRE;
	}

	public State getStatePost(){
		return State.POST;
	}

	private static void call(Event event) {
		ArrayHelper<Data> dataList = Asyncware.instance.eventManager.get(event.getClass());
		if (dataList != null) {
			for (Data data : dataList) {
				try {
					data.target.invoke(data.source, event);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
