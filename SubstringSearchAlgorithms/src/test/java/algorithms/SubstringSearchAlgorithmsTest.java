package algorithms;

import org.junit.jupiter.api.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SubstringSearchAlgorithmsTest {

        @BeforeEach
        public void init(TestInfo testInfo){
            String methodName = testInfo.getTestMethod().orElseThrow().getName();
            System.out.println("Method: "+methodName);
        }

        @AfterEach
        public void printMemoryUsage (){
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            double memory = (double)(runtime.totalMemory() - runtime.freeMemory());
            System.out.printf("\nMemory used: %.2f KBytes", memory/1024);
        }

        @Test
        public void runBenchmarks() throws Exception{
            Options options = new OptionsBuilder()
                    .include(this.getClass().getName() + ".*")
                    .mode(Mode.AverageTime)
                    .timeUnit(TimeUnit.MILLISECONDS)
                    .warmupTime(TimeValue.seconds(1))
                    .warmupIterations(2)
                    .threads(1)
                    .measurementIterations(5)
                    .forks(1)
                    .shouldFailOnError(true)
                    .shouldDoGC(true)
                    .build();
            new Runner(options).run();
        }

        @Benchmark
        @Test
        public void directSearchOneMatches () {
            String input = "What does the brain matter";
            String pattern = "the";
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.directSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void directSearchNoneMatches (){
            String input = "What does the brain matter";
            String pattern = "abra";
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.directSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void directSearchManyMatches (){
            String input = "What does the brain matter does the the does the";
            String pattern = "the";
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.directSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void kmpSearchOneMatches () {
            String input = "What does the brain matter";
            String pattern = "the";
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.kmpSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void kmpSearchNoneMatches (){
            String input = "What does the brain matter";
            String pattern = "abra";
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.kmpSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void kmpSearchManyMatches (){
            String input = "What does the brain matter does the the does the";
            String pattern = "the";
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.kmpSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void bmSearchManyMatches (){
            String input = "What does the brain matter does the the does the";
            String pattern = "the";
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.bmSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void bmSearchOneMatches () {
            String input = "What does the brain matter";
            String pattern = "the";
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.bmSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void bmSearchNoneMatches (){
            String input = "What does the brain matter";
            String pattern = "abra";
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.bmSearch(input, pattern));
        }


        @Benchmark
        @Test
        public void rkSearchManyMatches (){
            String input = "What does the brain matter does the the does the";
            String pattern = "the";
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.rkSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void rkSearchOneMatches () {
            String input = "What does the brain matter";
            String pattern = "the";
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.rkSearch(input, pattern));
        }

        @Benchmark
        @Test
        public void rkSearchNoneMatches (){
            String input = "What does the brain matter";
            String pattern = "abra";
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.rkSearch(input, pattern));
        }

}