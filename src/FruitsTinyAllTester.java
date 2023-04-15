public class FruitsTinyAllTester {
    public static void main(String[] args) throws Exception {
        ProjectTester tester = new ProjectTesterImp(); //Instantiate your own ProjectTester instance here

        System.out.println("Start crawl...");
        long s = System.currentTimeMillis();

        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");

        long e = System.currentTimeMillis();
        System.out.println("Crawl took: " + ((e-s)/1000.0) + "s");

        FruitsTinyOutgoingLinksTester.runTest(tester);
        FruitsTinyIncomingLinksTester.runTest(tester);
        FruitsTinyPageRanksTester.runTest(tester);
        FruitsTinyIDFTester.runTest(tester);
        FruitsTinyTFTester.runTest(tester);
        FruitsTinyTFIDFTester.runTest(tester);

        System.out.println("Start...");
        long start = System.currentTimeMillis();

        FruitsTinySearchTester.runTest(tester);

        long end = System.currentTimeMillis();
        System.out.println("Search took: " + ((end-start)/1000.0) + "s");

        System.out.println("Finished running all tests.");
    }
}
