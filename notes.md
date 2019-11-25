# info

videos can be downloaded by using:

    https://v.redd.it/[KEY]/DASH_2_4_M

example:

    https://v.redd.it/5w5d3n06lzvz/DASH_2_4_M


audio can be downloaded with:

    https://v.redd.it/[KEY]/audio?source=fallback

example:

    https://v.redd.it/zxb48usmsb821/audio?source=fallback
    
## add audio:


You wrap each raw format file into an appropriate Track object.

    H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl("video.h264"));
    AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl("audio.aac"));

These Track object are then added to a Movie object

    Movie movie = new Movie();
    movie.addTrack(h264Track);
    movie.addTrack(aacTrack);

The Movie object is fed into an MP4Builder to create the container.

    Container mp4file = new DefaultMp4Builder().build(movie);

Write the container to an appropriate sink.

    FileChannel fc = new FileOutputStream(new File("output.mp4")).getChannel();
    mp4file.writeContainer(fc);
    fc.close();