const gulp = require('gulp');
const ser = gulp.series;
const par = gulp.parallel;
const task = gulp.task;
const sass = require('gulp-sass');
const sourcemaps = require('gulp-sourcemaps');
const del = require('del');
const notify = require('gulp-notify');
const tildeImporter = require('node-sass-tilde-importer');
const browserSync = require('browser-sync').create();
const rigger = require('gulp-rigger');

const paths = {
  dest: 'build/dest/',
  destWatch: 'build/dest/**/*',
  rigger: {
    src: ['source/rigger/*.html', '!source/rigger/_*'],
    srcWatch: 'source/rigger/**/*',
    dest: 'build/dest/',
  },
  styles: {
    src: 'source/styles/*.scss',
    srcWatch: 'source/styles/**/*',
    dest: 'build/dest/css/'
  },
};

task('start', (ok) => {
  console.log('asd');
  ok();
});

task('rigger', () => {
  return gulp.src(paths.rigger.src)
    .pipe(rigger())
    .pipe(gulp.dest(paths.rigger.dest))
    ;
});

task('rigger-watch', () => {
  gulp.watch(paths.rigger.srcWatch, ser('rigger'))
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
  gulp.watch(paths.styles.srcWatch, ser('sass'))
});

task('clean', () => del(['build/dest']));

task('browser-sync', () => {
  browserSync.init({
    server: paths.dest,
  });

  gulp.watch(paths.destWatch).on('change', browserSync.reload);
});

task('watch', ser(
  'clean',
  par('sass', 'rigger'),
  par('sass-watch', 'rigger-watch', 'browser-sync'))
);