var authors = authorRepository.findAll();

authors.forEach(function(author) {
    var score = scoreService.getScore(author, new Date().getFullYear());
    author.setScore(score);
    authorRepository.save(author);
});