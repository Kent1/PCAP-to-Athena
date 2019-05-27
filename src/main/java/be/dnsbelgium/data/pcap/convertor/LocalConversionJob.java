package be.dnsbelgium.data.pcap.convertor;

import be.dnsbelgium.data.pcap.model.ServerInfo;
import be.dnsbelgium.data.pcap.aws.s3.ParquetFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * This class records the local PCAP files to convert
 */
public class LocalConversionJob {

  private final ServerInfo serverInfo;
  private final List<File> pcapFiles;
  private final long totalPcapBytes;
  private final File parquetOutputFolder;
  private final List<ParquetFile> parquetFiles;
  private Long totalParquetBytes = 0L;

  public LocalConversionJob(ServerInfo serverInfo, List<File> pcapFiles, File parquetOutputFolder) {
    this.serverInfo = serverInfo;

    this.pcapFiles = pcapFiles;
    this.totalPcapBytes = pcapFiles.stream().mapToLong(File::length).sum();

    this.parquetOutputFolder = parquetOutputFolder;
    this.parquetFiles = new ArrayList<>();
  }

  /**
   * return a list of all days for which this job has created Parquet files
   *
   * @return an ordered list of LocalDate objects
   */
  public List<LocalDate> getDaysCovered() {
    return parquetFiles.stream()
        .map(ParquetFile::getDate)
        .distinct()
        .filter(Objects::nonNull)
        .sorted()
        .collect(Collectors.toList());
  }

  public void addParquetFile(ParquetFile parquetFile) {
    parquetFiles.add(parquetFile);
    totalParquetBytes += parquetFile.size();
  }

  public ServerInfo getServerInfo() {
    return serverInfo;
  }

  public List<File> getPcapFiles() {
    return pcapFiles;
  }

  public List<ParquetFile> getParquetFiles() {
    return parquetFiles;
  }

  public File getParquetOutputFolder() {
    return parquetOutputFolder;
  }

  public long getTotalPcapBytes() {
    return totalPcapBytes;
  }

  public long getTotalParquetBytes() {
    return totalParquetBytes;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", LocalConversionJob.class.getSimpleName() + "[", "]")
        .add("serverInfo=" + serverInfo)
        .add("pcapFiles=" + pcapFiles.size())
        .add("totalPcapBytes=" + totalPcapBytes)
        .add("parquetFiles=" + parquetFiles.size())
        .add("totalParquetBytes=" + totalParquetBytes)
        .add("parquetOutputFolder=" + parquetOutputFolder)
        .toString();
  }

}
