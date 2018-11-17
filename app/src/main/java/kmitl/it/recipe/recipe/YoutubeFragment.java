//package kmitl.it.recipe.recipe;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;
//
//public class YoutubeFragment extends YouTubeBaseActivity {
//
//    //Youtube
//    YouTubePlayerView mYouTubePlayerView;
//    Button btnPlay;
//    YouTubePlayer.OnInitializedListener mOnInitialzedListener;
//
//    @Override
//    protected void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        setContentView(R.layout.fragment_youtube);
//
//
//        btnPlay = (Button) findViewById(R.id.btnPlay);
//        mYouTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubePlay);
//
//        mOnInitialzedListener = new YouTubePlayer.OnInitializedListener(){
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                Log.d("YOUTUBE","Success Intializing Youtube Player");
//                youTubePlayer.loadVideo("W4hTJybfU7s");
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Log.d("YOUTUBE","Fail to Intializing Youtube Player");
//            }
//        };
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("YOUTUBE","onClick: Intializing Youtube Player");
//                mYouTubePlayerView.initialize(YoutubeConfig.getApiKey(),mOnInitialzedListener);
//                Log.d("YOUTUBE","Done Intializing.");
//            }
//        });
//    }
//
//
//}
