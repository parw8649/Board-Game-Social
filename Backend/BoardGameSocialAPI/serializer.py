from rest_framework import serializers
from rest_framework.authtoken.models import Token
from .models import *


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'date_joined',
            'email',
            'first_name',
            'id',
            'is_active',
            'is_staff',
            'is_superuser',
            'last_login',
            'last_name',
            'password',
            'username'
        ]

    def create(self, validated_data):
        user = User.objects.create_user(**validated_data)
        Token.objects.create(user=user)
        return user


class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = [
            'bio',
            'iconUrl',
        ]


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = [
            'id',
            'name',
            'hostUserId',
            'dateTime',
            'description',
            'hostedGames'
        ]


class PostSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = [
            'id',
            'userId',
            'postBody',
            'postType',
            'dateTime',
            'private',
            'likes'
        ]


class GameSerializer(serializers.ModelSerializer):
    class Meta:
        model = Game
        fields = [
            'id',
            'gameTitle',
            'genre',
            'minPlayer',
            'maxPlayer',
            'description',
            'imageUrl',
            'overallPlayCount'
        ]


class UserToUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserToUser
        fields = [
            'id',
            'userOneId',
            'userTwoId'
        ]


class MessageSerializer(serializers.ModelSerializer):
    class Meta:
        model = Message
        fields = [
            'id',
            'senderId',
            'receiverId',
            'dateTime',
            'content'
        ]


class EventToUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = EventToUser
        fields = [
            'id',
            'userId',
            'eventId'
        ]


class CommentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Comment
        fields = [
            'id',
            'userId',
            'postId',
            'content'
        ]


class GameToUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = GameToUser
        fields = [
            'id',
            'userId',
            'gameId',
            'private'
        ]


class HostedGameSerializer(serializers.ModelSerializer):
    class Meta:
        model = HostedGame
        fields = [
            'id',
            'eventId',
            'gameId',
            'seatsAvailable'
        ]


class ReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = Review
        fields = [
            'id',
            'userId',
            'gameId',
            'content'
        ]


class HostedGameToUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = HostedGameToUser
        fields = [
            'id',
            'userId',
            'gameId'
        ]
