package io.github.zyrouge.symphony.services.login;

import android.util.Log;

import io.github.zyrouge.symphony.App;
import io.github.zyrouge.symphony.services.interfaces.SystemCallback;
import io.github.zyrouge.symphony.services.repository.SystemRepository;
import io.github.zyrouge.symphony.utils.Preferences;

public class Login {

    public void login() {
        String serverId = "codefish";
        String server = "https://music.15731573.xyz";
        String user = "codefish";
        String password = "10bsyr1000lblx@Zxj";
        saveServerPreference(serverId, server, user, password, false);
        SystemRepository systemRepository = new SystemRepository();
        systemRepository.checkUserCredential(new SystemCallback() {
            @Override
            public void onError(Exception exception) {
                resetServerPreference();
                Log.d("Lee4", "onError: 登录失败:"+exception);
            }

            @Override
            public void onSuccess(String password, String token, String salt) {
//                activity.goFromLogin();
                Log.d("Lee4", "onSuccess: 登录成功");
            }
        });
    }

    private void saveServerPreference(String serverId, String server, String user, String password, boolean isLowSecurity) {
        Preferences.setServerId(serverId);
        Preferences.setServer(server);
        Preferences.setUser(user);
        Preferences.setPassword(password);
        Preferences.setLowSecurity(isLowSecurity);
        App.getSubsonicClientInstance(true);
    }

    private void resetServerPreference() {
        Preferences.setServerId(null);
        Preferences.setServer(null);
        Preferences.setUser(null);
        Preferences.setPassword(null);
        Preferences.setToken(null);
        Preferences.setSalt(null);
        Preferences.setLowSecurity(false);

        App.getSubsonicClientInstance(true);
    }

//
//    private void initSyncStarredView() {
//        if (Preferences.isStarredSyncEnabled()) {
//            homeViewModel.getAllStarredTracks().observeForever(new Observer<List<Child>>() {
//                @Override
//                public void onChanged(List<Child> songs) {
//                    if (songs != null) {
//                        DownloaderManager manager = DownloadUtil.getDownloadTracker(requireContext());
//                        List<String> toSync = new ArrayList<>();
//
//                        for (Child song : songs) {
//                            if (!manager.isDownloaded(song.getId())) {
//                                toSync.add(song.getTitle());
//                            }
//                        }
//
//                        if (!toSync.isEmpty()) {
//                            bind.homeSyncStarredCard.setVisibility(View.VISIBLE);
//                            bind.homeSyncStarredTracksToSync.setText(String.join(", ", toSync));
//                        }
//                    }
//
//                    homeViewModel.getAllStarredTracks().removeObserver(this);
//                }
//            });
//        }
//
//        bind.homeSyncStarredCancel.setOnClickListener(v -> bind.homeSyncStarredCard.setVisibility(View.GONE));
//
//        bind.homeSyncStarredDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                homeViewModel.getAllStarredTracks().observeForever(new Observer<List<Child>>() {
//                    @Override
//                    public void onChanged(List<Child> songs) {
//                        if (songs != null) {
//                            DownloaderManager manager = DownloadUtil.getDownloadTracker(requireContext());
//
//                            for (Child song : songs) {
//                                if (!manager.isDownloaded(song.getId())) {
//                                    manager.download(MappingUtil.mapDownload(song), new Download(song));
//                                }
//                            }
//                        }
//
//                        homeViewModel.getAllStarredTracks().removeObserver(this);
//                        bind.homeSyncStarredCard.setVisibility(View.GONE);
//                    }
//                });
//            }
//        });
//    }
//
//    private void initDiscoverSongSlideView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_DISCOVERY)) return;
//
//        bind.discoverSongViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//
//        discoverSongAdapter = new DiscoverSongAdapter(this);
//        bind.discoverSongViewPager.setAdapter(discoverSongAdapter);
//        bind.discoverSongViewPager.setOffscreenPageLimit(1);
//        homeViewModel.getDiscoverSongSample(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), songs -> {
//            MusicUtil.ratingFilter(songs);
//
//            if (songs == null) {
//                if (bind != null) bind.homeDiscoverSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeDiscoverSector.setVisibility(!songs.isEmpty() ? View.VISIBLE : View.GONE);
//
//                discoverSongAdapter.setItems(songs);
//            }
//        });
//
//        setSlideViewOffset(bind.discoverSongViewPager, 20, 16);
//    }
//
//    private void initSimilarSongView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_MADE_FOR_YOU)) return;
//
//        bind.similarTracksRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        bind.similarTracksRecyclerView.setHasFixedSize(true);
//
//        similarMusicAdapter = new SimilarTrackAdapter(this);
//        bind.similarTracksRecyclerView.setAdapter(similarMusicAdapter);
//        homeViewModel.getStarredTracksSample(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), songs -> {
//            MusicUtil.ratingFilter(songs);
//
//            if (songs == null) {
//                if (bind != null) bind.homeSimilarTracksSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeSimilarTracksSector.setVisibility(!songs.isEmpty() ? View.VISIBLE : View.GONE);
//
//                similarMusicAdapter.setItems(songs);
//            }
//        });
//
//        CustomLinearSnapHelper similarSongSnapHelper = new CustomLinearSnapHelper();
//        similarSongSnapHelper.attachToRecyclerView(bind.similarTracksRecyclerView);
//    }
//
//    private void initArtistBestOf() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_BEST_OF)) return;
//
//        bind.bestOfArtistRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        bind.bestOfArtistRecyclerView.setHasFixedSize(true);
//
//        bestOfArtistAdapter = new ArtistAdapter(this, false, true);
//        bind.bestOfArtistRecyclerView.setAdapter(bestOfArtistAdapter);
//        homeViewModel.getBestOfArtists(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), artists -> {
//            if (artists == null) {
//                if (bind != null) bind.homeBestOfArtistSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeBestOfArtistSector.setVisibility(!artists.isEmpty() ? View.VISIBLE : View.GONE);
//
//                bestOfArtistAdapter.setItems(artists);
//            }
//        });
//
//        CustomLinearSnapHelper artistBestOfSnapHelper = new CustomLinearSnapHelper();
//        artistBestOfSnapHelper.attachToRecyclerView(bind.bestOfArtistRecyclerView);
//    }
//
//    private void initArtistRadio() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_RADIO_STATION)) return;
//
//        bind.radioArtistRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        bind.radioArtistRecyclerView.setHasFixedSize(true);
//
//        radioArtistAdapter = new ArtistAdapter(this, true, false);
//        bind.radioArtistRecyclerView.setAdapter(radioArtistAdapter);
//        homeViewModel.getStarredArtistsSample(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), artists -> {
//            if (artists == null) {
//                if (bind != null) bind.homeRadioArtistSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeRadioArtistSector.setVisibility(!artists.isEmpty() ? View.VISIBLE : View.GONE);
//                if (bind != null)
//                    bind.afterRadioArtistDivider.setVisibility(!artists.isEmpty() ? View.VISIBLE : View.GONE);
//
//                radioArtistAdapter.setItems(artists);
//            }
//        });
//
//        CustomLinearSnapHelper artistRadioSnapHelper = new CustomLinearSnapHelper();
//        artistRadioSnapHelper.attachToRecyclerView(bind.radioArtistRecyclerView);
//    }
//
//    private void initGridView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_TOP_SONGS)) return;
//
//        bind.gridTracksRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
//        bind.gridTracksRecyclerView.addItemDecoration(new GridItemDecoration(3, 8, false));
//        bind.gridTracksRecyclerView.setHasFixedSize(true);
//
//        gridTrackAdapter = new GridTrackAdapter(this);
//        bind.gridTracksRecyclerView.setAdapter(gridTrackAdapter);
//
//        homeViewModel.getDiscoverSongSample(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), music -> {
//            if (music != null) {
//                homeViewModel.getGridSongSample(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), chronologies -> {
//                    if (chronologies == null || chronologies.isEmpty()) {
//                        if (bind != null) bind.homeGridTracksSector.setVisibility(View.GONE);
//                        if (bind != null) bind.afterGridDivider.setVisibility(View.GONE);
//                    } else {
//                        if (bind != null) bind.homeGridTracksSector.setVisibility(View.VISIBLE);
//                        if (bind != null) bind.afterGridDivider.setVisibility(View.VISIBLE);
//                        gridTrackAdapter.setItems(chronologies);
//                    }
//                });
//            }
//        });
//    }
//
//    private void initStarredTracksView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_STARRED_TRACKS)) return;
//
//        bind.starredTracksRecyclerView.setHasFixedSize(true);
//
//        starredSongAdapter = new SongHorizontalAdapter(this, true, false);
//        bind.starredTracksRecyclerView.setAdapter(starredSongAdapter);
//        homeViewModel.getStarredTracks(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), songs -> {
//            if (songs == null) {
//                if (bind != null) bind.starredTracksSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.starredTracksSector.setVisibility(!songs.isEmpty() ? View.VISIBLE : View.GONE);
//                if (bind != null)
//                    bind.starredTracksRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), UIUtil.getSpanCount(songs.size(), 5), GridLayoutManager.HORIZONTAL, false));
//
//                starredSongAdapter.setItems(songs);
//            }
//        });
//
//        SnapHelper starredTrackSnapHelper = new PagerSnapHelper();
//        starredTrackSnapHelper.attachToRecyclerView(bind.starredTracksRecyclerView);
//
//        bind.starredTracksRecyclerView.addItemDecoration(
//                new DotsIndicatorDecoration(
//                        getResources().getDimensionPixelSize(R.dimen.radius),
//                        getResources().getDimensionPixelSize(R.dimen.radius) * 4,
//                        getResources().getDimensionPixelSize(R.dimen.dots_height),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null))
//        );
//    }
//
//    private void initStarredAlbumsView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_STARRED_ALBUMS)) return;
//
//        bind.starredAlbumsRecyclerView.setHasFixedSize(true);
//
//        starredAlbumAdapter = new AlbumHorizontalAdapter(this, false);
//        bind.starredAlbumsRecyclerView.setAdapter(starredAlbumAdapter);
//        homeViewModel.getStarredAlbums(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), albums -> {
//            if (albums == null) {
//                if (bind != null) bind.starredAlbumsSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.starredAlbumsSector.setVisibility(!albums.isEmpty() ? View.VISIBLE : View.GONE);
//                if (bind != null)
//                    bind.starredAlbumsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), UIUtil.getSpanCount(albums.size(), 5), GridLayoutManager.HORIZONTAL, false));
//
//                starredAlbumAdapter.setItems(albums);
//            }
//        });
//
//        SnapHelper starredAlbumSnapHelper = new PagerSnapHelper();
//        starredAlbumSnapHelper.attachToRecyclerView(bind.starredAlbumsRecyclerView);
//
//        bind.starredAlbumsRecyclerView.addItemDecoration(
//                new DotsIndicatorDecoration(
//                        getResources().getDimensionPixelSize(R.dimen.radius),
//                        getResources().getDimensionPixelSize(R.dimen.radius) * 4,
//                        getResources().getDimensionPixelSize(R.dimen.dots_height),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null))
//        );
//    }
//
//    private void initStarredArtistsView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_STARRED_ARTISTS)) return;
//
//        bind.starredArtistsRecyclerView.setHasFixedSize(true);
//
//        starredArtistAdapter = new ArtistHorizontalAdapter(this);
//        bind.starredArtistsRecyclerView.setAdapter(starredArtistAdapter);
//        homeViewModel.getStarredArtists(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), artists -> {
//            if (artists == null) {
//                if (bind != null) bind.starredArtistsSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.starredArtistsSector.setVisibility(!artists.isEmpty() ? View.VISIBLE : View.GONE);
//                if (bind != null)
//                    bind.afterFavoritesDivider.setVisibility(!artists.isEmpty() ? View.VISIBLE : View.GONE);
//                if (bind != null)
//                    bind.starredArtistsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), UIUtil.getSpanCount(artists.size(), 5), GridLayoutManager.HORIZONTAL, false));
//
//                starredArtistAdapter.setItems(artists);
//            }
//        });
//
//        SnapHelper starredArtistSnapHelper = new PagerSnapHelper();
//        starredArtistSnapHelper.attachToRecyclerView(bind.starredArtistsRecyclerView);
//
//        bind.starredArtistsRecyclerView.addItemDecoration(
//                new DotsIndicatorDecoration(
//                        getResources().getDimensionPixelSize(R.dimen.radius),
//                        getResources().getDimensionPixelSize(R.dimen.radius) * 4,
//                        getResources().getDimensionPixelSize(R.dimen.dots_height),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null))
//        );
//    }
//
//    private void initNewReleasesView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_NEW_RELEASES)) return;
//
//        bind.newReleasesRecyclerView.setHasFixedSize(true);
//
//        newReleasesAlbumAdapter = new AlbumHorizontalAdapter(this, false);
//        bind.newReleasesRecyclerView.setAdapter(newReleasesAlbumAdapter);
//        homeViewModel.getRecentlyReleasedAlbums(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), albums -> {
//            if (albums == null) {
//                if (bind != null) bind.homeNewReleasesSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeNewReleasesSector.setVisibility(!albums.isEmpty() ? View.VISIBLE : View.GONE);
//                if (bind != null)
//                    bind.newReleasesRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), UIUtil.getSpanCount(albums.size(), 5), GridLayoutManager.HORIZONTAL, false));
//
//                newReleasesAlbumAdapter.setItems(albums);
//            }
//        });
//
//        SnapHelper newReleasesSnapHelper = new PagerSnapHelper();
//        newReleasesSnapHelper.attachToRecyclerView(bind.newReleasesRecyclerView);
//
//        bind.newReleasesRecyclerView.addItemDecoration(
//                new DotsIndicatorDecoration(
//                        getResources().getDimensionPixelSize(R.dimen.radius),
//                        getResources().getDimensionPixelSize(R.dimen.radius) * 4,
//                        getResources().getDimensionPixelSize(R.dimen.dots_height),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null))
//        );
//    }
//
//    private void initYearSongView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_FLASHBACK)) return;
//
//        bind.yearsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        bind.yearsRecyclerView.setHasFixedSize(true);
//
//        yearAdapter = new YearAdapter(this);
//        bind.yearsRecyclerView.setAdapter(yearAdapter);
//        homeViewModel.getYearList(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), years -> {
//            if (years == null) {
//                if (bind != null) bind.homeFlashbackSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeFlashbackSector.setVisibility(!years.isEmpty() ? View.VISIBLE : View.GONE);
//
//                yearAdapter.setItems(years);
//            }
//        });
//
//        CustomLinearSnapHelper yearSnapHelper = new CustomLinearSnapHelper();
//        yearSnapHelper.attachToRecyclerView(bind.yearsRecyclerView);
//    }
//
//    private void initMostPlayedAlbumView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_MOST_PLAYED)) return;
//
//        bind.mostPlayedAlbumsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        bind.mostPlayedAlbumsRecyclerView.setHasFixedSize(true);
//
//        mostPlayedAlbumAdapter = new AlbumAdapter(this);
//        bind.mostPlayedAlbumsRecyclerView.setAdapter(mostPlayedAlbumAdapter);
//        homeViewModel.getMostPlayedAlbums(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), albums -> {
//            if (albums == null) {
//                if (bind != null) bind.homeMostPlayedAlbumsSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeMostPlayedAlbumsSector.setVisibility(!albums.isEmpty() ? View.VISIBLE : View.GONE);
//
//                mostPlayedAlbumAdapter.setItems(albums);
//            }
//        });
//
//        CustomLinearSnapHelper mostPlayedAlbumSnapHelper = new CustomLinearSnapHelper();
//        mostPlayedAlbumSnapHelper.attachToRecyclerView(bind.mostPlayedAlbumsRecyclerView);
//    }
//
//    private void initRecentPlayedAlbumView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_LAST_PLAYED)) return;
//
//        bind.recentlyPlayedAlbumsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        bind.recentlyPlayedAlbumsRecyclerView.setHasFixedSize(true);
//
//        recentlyPlayedAlbumAdapter = new AlbumAdapter(this);
//        bind.recentlyPlayedAlbumsRecyclerView.setAdapter(recentlyPlayedAlbumAdapter);
//        homeViewModel.getRecentlyPlayedAlbumList(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), albums -> {
//            if (albums == null) {
//                if (bind != null) bind.homeRecentlyPlayedAlbumsSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeRecentlyPlayedAlbumsSector.setVisibility(!albums.isEmpty() ? View.VISIBLE : View.GONE);
//
//                recentlyPlayedAlbumAdapter.setItems(albums);
//            }
//        });
//
//        CustomLinearSnapHelper recentPlayedAlbumSnapHelper = new CustomLinearSnapHelper();
//        recentPlayedAlbumSnapHelper.attachToRecyclerView(bind.recentlyPlayedAlbumsRecyclerView);
//    }
//
//    private void initRecentAddedAlbumView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_RECENTLY_ADDED)) return;
//
//        bind.recentlyAddedAlbumsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        bind.recentlyAddedAlbumsRecyclerView.setHasFixedSize(true);
//
//        recentlyAddedAlbumAdapter = new AlbumAdapter(this);
//        bind.recentlyAddedAlbumsRecyclerView.setAdapter(recentlyAddedAlbumAdapter);
//        homeViewModel.getMostRecentlyAddedAlbums(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), albums -> {
//            if (albums == null) {
//                if (bind != null) bind.homeRecentlyAddedAlbumsSector.setVisibility(View.GONE);
//            } else {
//                if (bind != null)
//                    bind.homeRecentlyAddedAlbumsSector.setVisibility(!albums.isEmpty() ? View.VISIBLE : View.GONE);
//
//                recentlyAddedAlbumAdapter.setItems(albums);
//            }
//        });
//
//        CustomLinearSnapHelper recentAddedAlbumSnapHelper = new CustomLinearSnapHelper();
//        recentAddedAlbumSnapHelper.attachToRecyclerView(bind.recentlyAddedAlbumsRecyclerView);
//    }
//
//    private void initSharesView() {
//        if (homeViewModel.checkHomeSectorVisibility(Constants.HOME_SECTOR_SHARED)) return;
//
//        bind.sharesRecyclerView.setHasFixedSize(true);
//
//        shareHorizontalAdapter = new ShareHorizontalAdapter(this);
//        bind.sharesRecyclerView.setAdapter(shareHorizontalAdapter);
//        if (Preferences.isSharingEnabled()) {
//            homeViewModel.getShares(getViewLifecycleOwner()).observe(getViewLifecycleOwner(), shares -> {
//                if (shares == null) {
//                    if (bind != null) bind.sharesSector.setVisibility(View.GONE);
//                } else {
//                    if (bind != null)
//                        bind.sharesSector.setVisibility(!shares.isEmpty() ? View.VISIBLE : View.GONE);
//                    if (bind != null)
//                        bind.sharesRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), UIUtil.getSpanCount(shares.size(), 10), GridLayoutManager.HORIZONTAL, false));
//
//                    shareHorizontalAdapter.setItems(shares);
//                }
//            });
//        }
//
//        SnapHelper starredTrackSnapHelper = new PagerSnapHelper();
//        starredTrackSnapHelper.attachToRecyclerView(bind.sharesRecyclerView);
//
//        bind.sharesRecyclerView.addItemDecoration(
//                new DotsIndicatorDecoration(
//                        getResources().getDimensionPixelSize(R.dimen.radius),
//                        getResources().getDimensionPixelSize(R.dimen.radius) * 4,
//                        getResources().getDimensionPixelSize(R.dimen.dots_height),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null),
//                        requireContext().getResources().getColor(R.color.titleTextColor, null))
//        );
//    }
//
//    private void initHomeReorganizer() {
//        final Handler handler = new Handler();
//        final Runnable runnable = () -> { if (bind != null) bind.homeSectorRearrangementButton.setVisibility(View.VISIBLE); };
//        handler.postDelayed(runnable, 5000);
//
//        bind.homeSectorRearrangementButton.setOnClickListener(v -> {
//            HomeRearrangementDialog dialog = new HomeRearrangementDialog();
//            dialog.show(requireActivity().getSupportFragmentManager(), null);
//        });
//    }
//
//    private void refreshSharesView() {
//        final Handler handler = new Handler();
//        final Runnable runnable = () -> {
//            if (getView() != null && bind != null && Preferences.isSharingEnabled()) {
//                homeViewModel.refreshShares(getViewLifecycleOwner());
//            }
//        };
//        handler.postDelayed(runnable, 100);
//    }
//
//    private void setSlideViewOffset(ViewPager2 viewPager, float pageOffset, float pageMargin) {
//        viewPager.setPageTransformer((page, position) -> {
//            float myOffset = position * -(2 * pageOffset + pageMargin);
//            if (viewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                    page.setTranslationX(-myOffset);
//                } else {
//                    page.setTranslationX(myOffset);
//                }
//            } else {
//                page.setTranslationY(myOffset);
//            }
//        });
//    }
//
//    public void reorder() {
//        if (bind != null && homeViewModel.getHomeSectorList() != null) {
//            bind.homeLinearLayoutContainer.removeAllViews();
//
//            for (HomeSector sector : homeViewModel.getHomeSectorList()) {
//                if (!sector.isVisible()) continue;
//
//                switch (sector.getId()) {
//                    case Constants.HOME_SECTOR_DISCOVERY:
//                        bind.homeLinearLayoutContainer.addView(bind.homeDiscoverSector);
//                        break;
//                    case Constants.HOME_SECTOR_MADE_FOR_YOU:
//                        bind.homeLinearLayoutContainer.addView(bind.homeSimilarTracksSector);
//                        break;
//                    case Constants.HOME_SECTOR_BEST_OF:
//                        bind.homeLinearLayoutContainer.addView(bind.homeBestOfArtistSector);
//                        break;
//                    case Constants.HOME_SECTOR_RADIO_STATION:
//                        bind.homeLinearLayoutContainer.addView(bind.homeRadioArtistSector);
//                        break;
//                    case Constants.HOME_SECTOR_TOP_SONGS:
//                        bind.homeLinearLayoutContainer.addView(bind.homeGridTracksSector);
//                        break;
//                    case Constants.HOME_SECTOR_STARRED_TRACKS:
//                        bind.homeLinearLayoutContainer.addView(bind.starredTracksSector);
//                        break;
//                    case Constants.HOME_SECTOR_STARRED_ALBUMS:
//                        bind.homeLinearLayoutContainer.addView(bind.starredAlbumsSector);
//                        break;
//                    case Constants.HOME_SECTOR_STARRED_ARTISTS:
//                        bind.homeLinearLayoutContainer.addView(bind.starredArtistsSector);
//                        break;
//                    case Constants.HOME_SECTOR_NEW_RELEASES:
//                        bind.homeLinearLayoutContainer.addView(bind.homeNewReleasesSector);
//                        break;
//                    case Constants.HOME_SECTOR_FLASHBACK:
//                        bind.homeLinearLayoutContainer.addView(bind.homeFlashbackSector);
//                        break;
//                    case Constants.HOME_SECTOR_MOST_PLAYED:
//                        bind.homeLinearLayoutContainer.addView(bind.homeMostPlayedAlbumsSector);
//                        break;
//                    case Constants.HOME_SECTOR_LAST_PLAYED:
//                        bind.homeLinearLayoutContainer.addView(bind.homeRecentlyPlayedAlbumsSector);
//                        break;
//                    case Constants.HOME_SECTOR_RECENTLY_ADDED:
//                        bind.homeLinearLayoutContainer.addView(bind.homeRecentlyAddedAlbumsSector);
//                        break;
//                    case Constants.HOME_SECTOR_SHARED:
//                        bind.homeLinearLayoutContainer.addView(bind.sharesSector);
//                        break;
//                }
//            }
//
//            bind.homeLinearLayoutContainer.addView(bind.homeSectorRearrangementButton);
//        }
//    }
//
//    private void initializeMediaBrowser() {
//        mediaBrowserListenableFuture = new MediaBrowser.Builder(requireContext(), new SessionToken(requireContext(), new ComponentName(requireContext(), MediaService.class))).buildAsync();
//    }

}


