QuickRecipe
Nama Kelompok
Julius Alfredo - 00000036372
Neldy Wijaya – 00000036532
Ruben Rafferty – 00000036238
Jason Tandiono – 00000035990
Felix Ferdinand – 00000035927

Tahap Pembukaan Project
1.Extract File
2.Buka File Project Melalui android studio
3.Jalankan menggunakan run / shift+f10

Aplikasi memakai firebase sebagai database, picasso sebagai library memasukkan image ke dalam view.

Pada build gradle, gradle-gradle yang diimport adalah

implementation 'com.firebaseui:firebase-ui-firestore:8.0.0'
implementation 'androidx.appcompat:appcompat:1.3.1'
implementation 'com.google.android.material:material:1.4.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.1'
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
implementation 'com.google.firebase:firebase-storage:20.0.0'
implementation 'com.google.firebase:firebase-auth:21.0.1'
implementation 'com.google.firebase:firebase-firestore:24.0.0'
implementation 'com.google.firebase:firebase-database:20.0.2'
testImplementation 'junit:junit:4.+'
androidTestImplementation 'androidx.test.ext:junit:1.1.3'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
implementation 'com.github.bumptech.glide:glide:4.11.0'
annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
implementation 'com.makeramen:roundedimageview:2.3.0'
implementation 'com.synnapps:carouselview:0.1.5'
implementation platform('com.google.firebase:firebase-bom:29.0.0')
api 'com.theartofdev.edmodo:android-image-cropper:2.8.+'
implementation 'com.squareup.picasso:picasso:2.5.2'
implementation 'com.firebaseui:firebase-ui-firestore:8.0.0'