const gulp = require('gulp');
const ser = gulp.series;
const par = gulp.parallel;
const task = gulp.task;
const pug = require('gulp-pug');
const sass = require('gulp-sass');
const sourcemaps = require('gulp-sourcemaps');
const del = require('del');
const notify = require('gulp-notify');
const tildeImporter = require('node-sass-tilde-importer');
const browserSync = require('browser-sync').create();

const paths = {
  dest: 'build/dest/',
  destWatch: 'build/dest/**/*',
  pug: {
    src: ['source/pug/*.pug', '!source/pug/_*'],
    srcWatch: 'source/pug/*.pug',
    dest: 'build/dest/',
  },
  styles: {
    src: 'source/styles/*.sass',
    dest: 'build/dest/css/'
  },
};

task('start', (ok) => {
  console.log('asd');
  ok();
});

task('pug', () => {
  return gulp.src(paths.pug.src)
    .pipe(pug({
      pretty: true,
    }))
    .on('error', notify.onError(function (error) {
      return 'An error occurred while compiling jade.\nLook in the console for details.\n' + error;
    }))
    .pipe(gulp.dest(paths.pug.dest));
});

task('pug-watch', () => {
  gulp.watch(paths.pug.srcWatch, ser('pug'))
});

task('sass', () => {
  return gulp.src(paths.styles.src)
    .pipe(sourcemaps.init())
    .pipe(sass({
      outputStyle: 'expanded',
      importer: tildeImporter,
    }).on('error', sass.logError))
    .pipe(sourcemaps.write())
    .pipe(gulp.dest(paths.styles.dest));
});

task('sass-watch', () => {
  gulp.watch(paths.styles.src, ser('sass'))
});

task('clean', () => del(['build']));

task('browser-sync', () => {
  browserSync.init({
    server: paths.dest,
  });

  gulp.watch(paths.destWatch).on('change', browserSync.reload);
});

task('watch', ser(
  par('sass', 'pug'),
  par('sass-watch', 'pug-watch', 'browser-sync'))
);
