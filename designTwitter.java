/*
 * Time complexity - O(nk) 
 * n - followers for the user
 * k - tweets by the user
 * Ignored case1: Logarithmic time to insert a tweet reason for ignored  the limitaion on  10 tweets in input data
 * Ignored case2: Adding result at index 0. Also adding the zero case
 * Worst case - O(nk log(nk))
 * Space Complexity - O()
 */


class Twitter {
    class Tweet{
        int tweetId;
        int createdAt;
        public Tweet(int tweetId, int createdAt){
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> allUsers = userMap.get(userId);
        for(Integer user : allUsers){
            List<Tweet> allTweets = tweetMap.get(user);
            if(allTweets != null){
                for(Tweet tw : allTweets){
                    pq.add(tw);
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            return;
        }
        if(userMap.get(followerId).contains(followeeId)){
            userMap.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */