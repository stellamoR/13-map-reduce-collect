package ohm.softa.a13.tweets;

import ohm.softa.a13.model.Tweet;
import ohm.softa.a13.utils.ResourceUtils;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingInt;

public class TrumpTweetStats {

    public static Map<String, Long> calculateSourceAppStats(Stream<Tweet> tweetStream) {
        /* TODO group the tweets by the `sourceApp` they were created with and count how many it were per `sourceApp` */
		return tweetStream
			.collect(Collectors.groupingBy(Tweet::getSourceApp, Collectors.counting()));
    }

	public static Map<String, Long> calculateSourceAppStatsReduce(Stream<Tweet> tweetStream) {
		/* TODO group the tweets by the `sourceApp` they were created with and count how many it were per `sourceApp` */
		return tweetStream
			.map(Tweet::getSourceApp)
			.reduce(new LinkedHashMap<String, Long>(), (map, app) -> {
				map.put(app, map.compute(app, (k,v) -> v == null?1:v+1));
				return map;
			}, (m1, m2) -> m1);

	}

    public static Map<String, Set<Tweet>> calculateTweetsBySourceApp(Stream<Tweet> tweetStream) {
        /* TODO group the tweets by the `sourceApp`
         * collect the tweets in `Set`s for each source app */
        return tweetStream
			.collect(Collectors.groupingBy(Tweet::getSourceApp, Collectors.toSet()));
    }
//	public static Map<String, Set<Tweet>> calculateTweetsBySourceAppReduce(Stream<Tweet> tweetStream) {
		/* TODO group the tweets by the `sourceApp`
		 * collect the tweets in `Set`s for each source app */
		//return tweetStream
			//.reduce(new HashMap<String, Set<Tweet>>(), (map, tweet) -> {
				//map.get(tweet.getSourceApp()).add(tweet);
				//			map.put(tweet.getSourceApp(), ))
	//	}, (m1, m2) -> m2);
	//
	//}



    public static Map<String, Integer> calculateWordCount(Stream<Tweet> tweetStream, List<String> stopWords) {
        /* Remark: implement this method at last */
        /* TODO split the tweets, lower them, trim them, remove all words that are in the `stopWords`,
         * reduce the result to a Map<String, Integer> to count how often each word were in the tweets
         * optionally you could filter for all words that were used more than 10 times */
		List<String> stopwords = ResourceUtils.loadStopWords();
		return tweetStream
			.map(tweet-> tweet.getText())
			.flatMap(text-> Stream.of(text.split(" ")))
			.map(String::toLowerCase)
			.filter(myString -> stopwords.stream().noneMatch(sw -> sw.equals(myString)))
			.collect(Collectors.groupingBy(mystring -> mystring,summingInt(mystring -> 1)));
    }
}
