package edu.hu.cisc525;

import java.io.IOException;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * This is an example of using Map/Reduce.  The example uses the 
 * standard FileInputFormat, which reads a file one line at a time and 
 * passes it to a map task.
 * 
 */
public class ExampleJob extends Configured implements Tool
{
	  public static class ExampleMapper extends Mapper<LongWritable, Text, Text, Text>
	  {
    // The setup function is called once.
    protected void setup(Context context) throws IOException, InterruptedException
    {

    }

    // Map is called once per line in the file. 
    // line is split in to words and each word is broken down to characters
    //sorted sequence of characters becomes the output key and word becomes value 
    public void map(Text key, Text value, Context context) throws IOException, InterruptedException
    {
      // takes each line as input to the mapper
      String line = value.toString();

      //remove all puntuation and numerics and make same case
      line.replaceAll("[^a-zA-Z ]", "").toLowerCase();

      //run mapper for each word
      String [] words = line.split(" ");

      //split each word to get character count set mapper output key as object and value as word
      for (String word : words) {
        char[] characters = word.toCharArray();
        // sort the character array
        Arrays.sort(characters);

        //convert the array to string to be sent to reducer
        String wordSequenced = Arrays.toString(characters);
        
      //characters of the word sorted in order becomes the key   
        Text keyOut = new Text(wordSequenced);
        
        //word becomes the value   
        Text valueOut = new Text(word);
        context.write(keyOut, valueOut);
      }
      

    }
  }

  /**
   * The reducer class gathers the output of the mappers.
   */
  public static class ExampleReducer extends Reducer<Text, Text, Text, Text>
  {

	    // The reduce method is called once for each key emitted by the mapper, with an iterable collection of
	    // each value emitted for that key.
	    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
	    {
	      // Your logic goes here

	      // For this example, the lines of the file are just written back out.
	      // Note that this does not guarantee that they will be in the same order as the input file.

	      StringBuilder anagramsBuilder = new StringBuilder();
	      int counter = 0;
	      for (Text s : values)
	      {
	        anagramsBuilder.append(s);
	        anagramsBuilder.append("     ");
	        counter = counter+1;
	      }
	      //writing out put only if there are anagrams 
	      if (counter >1){
	      String anagramsString = anagramsBuilder.toString();
	      Text anagrams = new Text(anagramsString);
	      context.write(key, anagrams);
	      }
	      }
	  }


  /**
   * Implements the run() method of the Tool interface.
   * 
   * @param args - Command line arguments.
   *               This example assumes that the first argument will be the
   *               name of an input file (located in the distributed file system) and
   *               the second argument will be the name of a directory (also in the DFS)
   *               where output should be written.  Note that the MapReduce framework will
   *               create the directory, and will produce an error if the directory 
   *               already exists.
   *               
   * @return 0 if the job is successful, 1 otherwise
   * 
   */
  @Override
  public int run(String[] args) throws Exception
  {
	    // Get the default configuration object
	    Configuration conf = new Configuration();

	    // Create a new job
	    Job job = new Job(conf, this.getClass().getName());

	    // Set the output types
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);

	    // Set the mapper and reducer classes
	    job.setMapperClass(ExampleMapper.class);
	    job.setReducerClass(ExampleReducer.class);
	    //Key will be the object containing all the alphabets in the word
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(Text.class);
	    // Set the input and output formats

	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);

	    // Set the input and output paths
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));

	    // set the jar file to run
	    job.setJarByClass(this.getClass());

	    // set the number of reducers to one
	    job.setNumReduceTasks(1);

	    // submit the job
	    int exitCode = job.waitForCompletion(true) ? 0 : 1;

	    // Return the exit code
	    return exitCode;
  }
  /**
   * Executes the job using the ToolRunner method.
   * @param args - Command line arguments.
   *               The first argument should contain the location of the input
   *               data file, e.g. /examples/data/LaptopSpecs.tsv
   *               The second argument should specify a directory in MapRFS to
   *               place the results, e.g. /examples/results/features
   *               
   * @return 0 if the job is successful, 1 otherwise
   * 
   */
  public static int main(String[] args)
  {
    int exitCode = 0;
    try
    {
      exitCode = ToolRunner.run(new ExampleJob(), args);
    }
    catch (Exception e)
    {
      System.err.println("Exception running ExampleJob:");
      e.printStackTrace(System.err);
    }
    return exitCode;
  }
}
