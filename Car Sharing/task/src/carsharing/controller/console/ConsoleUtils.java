package carsharing.controller.console;

import carsharing.controller.AppState;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConsoleUtils {

    public static <T> String[] generateMenuOptionsList(
            final List<T> menuEntities,
            final Function<Integer, String> mapper,
            final String... additionalOptions
    ) {
        final Stream<String> menuOptionsStream =
                IntStream.range(0, menuEntities.size())
                        .mapToObj(idx -> String.format("%d. %s", idx + 1, mapper.apply(idx)));
        return Stream.concat(
                menuOptionsStream,
                Stream.of(additionalOptions)
        ).toArray(String[]::new);
    }

    @SafeVarargs
    public static <T> Map<Integer, AppState> generateMenuOptionTransitions(
            final List<T> menuEntities,
            final Function<Integer, AppState> mapper,
            final AbstractMap.SimpleImmutableEntry<Integer, AppState>... additionalOptionTransitions
    ) {
        final Stream<AbstractMap.SimpleImmutableEntry<Integer, AppState>> menuOptionTransitionsStream =
                IntStream.range(0, menuEntities.size())
                        .mapToObj(idx -> new AbstractMap.SimpleImmutableEntry<>(idx + 1, mapper.apply(idx)));
        //noinspection unchecked
        return Map.ofEntries(Stream.concat(
                Stream.of(additionalOptionTransitions),
                menuOptionTransitionsStream
        ).toArray(AbstractMap.SimpleImmutableEntry[]::new));
    }
}
