from django.db import models
from django.contrib.auth.models import User


class Event(models.Model):
    class Meta:
        db_table = "Event"

    hostUserId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='hostUserId')
    date = models.DateTimeField(db_column='dateTime')
    description = models.TextField(db_column='description')
    hostedGames = models.IntegerField(db_column='hostedGames')


class Post(models.Model):
    class Meta:
        db_table = "Post"

    userId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userId')
    postBody = models.TextField(db_column='postBody')
    postType = models.CharField(db_column='postType', max_length=20)
    private = models.BooleanField(default=False, db_column='private')
    likes = models.IntegerField(db_column='likes')


class Game(models.Model):
    class Meta:
        db_table = "Game"

    gameTitle = models.CharField(db_column='gameTitle', max_length=200)
    genre = models.CharField(db_column='genre', max_length=20)
    minPlayer = models.IntegerField(db_column='minPlayer')
    maxPlayer = models.IntegerField(db_column='maxPlayer')
    description = models.TextField(db_column='description')
    imageUrl = models.TextField(db_column='imageUrl')
    overallPlayCount = models.IntegerField(default=0, db_column='overallPlayCount')


class UserToUser(models.Model):
    class Meta:
        db_table = "UserToUser"

    userOneId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userOneId')
    userTwoId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userTwoId')


class Massage(models.Model):
    class Meta:
        db_table = "Massage"

    senderId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='senderId')
    receiverId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='receiverId')
    content = models.TextField(db_column='content')


class EventToUser(models.Model):
    class Meta:
        db_table = "EventToUser"

    userId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userId')
    eventId = models.ForeignKey(Event, on_delete=models.CASCADE, db_column='eventId')


class Comment(models.Model):
    class Meta:
        db_table = "Comment"

    userId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userId')
    postId = models.ForeignKey(Post, on_delete=models.CASCADE, db_column='postId')


class GameToUser(models.Model):
    class Meta:
        db_table = "GameToUser"

    userId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userId')
    gameId = models.ForeignKey(Game, on_delete=models.CASCADE, db_column='gameId')
    private = models.BooleanField(db_column='private')


class HostedGame(models.Model):
    class Meta:
        db_table = "HostedGame"

    eventId = models.ForeignKey(Event, on_delete=models.CASCADE, db_column='eventId')
    gameId = models.ForeignKey(Game, on_delete=models.CASCADE, db_column='gameId')
    seatsAvailable = models.IntegerField(db_column='seatsAvailable')


class Review(models.Model):
    class Meta:
        db_table = "Review"

    userId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userId')
    gameId = models.ForeignKey(Game, on_delete=models.CASCADE, db_column='gameId')
    content = models.TextField(db_column='content')


class HostedGameToUser(models.Model):
    class Meta:
        db_table = "HostedGameToUser"

    userId = models.ForeignKey(User, on_delete=models.CASCADE, db_column='userId')
    gameId = models.ForeignKey(Game, on_delete=models.CASCADE, db_column='gameId')
