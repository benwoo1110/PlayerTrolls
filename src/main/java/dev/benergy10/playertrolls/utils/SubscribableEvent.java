package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class SubscribableEvent<T extends Event, S> {

    private final Class<T> eventClass;
    private final Set<S> subscribers;

    private boolean registered = false;
    private EventPriority priority = EventPriority.NORMAL;
    private boolean ignoreCancelled = false;
    private Function<T, S> eventTarget;
    private Consumer<T> handler;
    private boolean autoUnsubscribe;

    private SubscribableEvent(Class<T> eventClass) {
        this.eventClass = eventClass;
        this.subscribers = new HashSet<>();
    }

    public void register(Plugin plugin) {
        if (this.registered) {
            throw new IllegalArgumentException("Event already registered!");
        }
        Bukkit.getPluginManager().registerEvent(
                this.eventClass,
                new Listener() { },
                this.priority,
                this.createExecutor(),
                plugin,
                this.ignoreCancelled
        );
        this.registered = true;
    }

    private EventExecutor createExecutor() {
        return (listener, event) -> {
            if (this.subscribers.isEmpty() || !this.eventClass.isInstance(event)) {
                return;
            }
            T eventInstance = (T) event;
            S target = this.eventTarget.apply(eventInstance);
            if (!this.subscribers.contains(target)) {
                return;
            }
            this.handler.accept(eventInstance);
            if (this.autoUnsubscribe) {
                this.subscribers.remove(target);
            }
        };
    }

    public boolean subscribe(S s) {
        Logging.info("Subscribing %s from %s...", s, this.eventClass.getName());
        return this.subscribers.add(s);
    }

    public boolean unsubscribe(S s) {
        Logging.info("Unsubscribing %s from %s...", s, this.eventClass.getName());
        return this.subscribers.remove(s);
    }

    public Class<T> getEventClass() {
        return eventClass;
    }

    public Set<S> getSubscribers() {
        return subscribers;
    }

    public boolean isRegistered() {
        return registered;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }

    public Function<T, S> getEventTarget() {
        return eventTarget;
    }

    public Consumer<T> getHandler() {
        return handler;
    }

    public static class Creator<T extends Event, S> {

        private final SubscribableEvent<T, S> event;

        public Creator(Class<T> eventClass) {
            this.event = new SubscribableEvent<>(eventClass);
        }

        public Creator<T, S> priority(EventPriority priority) {
            this.event.priority = priority;
            return this;
        }

        public Creator<T, S> ignoreCancelled(boolean ignoreCancelled) {
            this.event.ignoreCancelled = ignoreCancelled;
            return this;
        }

        public Creator<T, S> eventTarget(Function<T, S> eventTarget) {
            this.event.eventTarget = eventTarget;
            return this;
        }

        public Creator<T, S> handler(Consumer<T> handler) {
            this.event.handler = handler;
            return this;
        }

        public Creator<T, S> autoUnsubscribe(boolean autoUnsubscribe) {
            this.event.autoUnsubscribe = autoUnsubscribe;
            return this;
        }

        public SubscribableEvent<T, S> create() {
            return this.event;
        }
    }
}
