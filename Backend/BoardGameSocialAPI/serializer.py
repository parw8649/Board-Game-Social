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


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        fields = [
            'hostUserId',
            'dateTime',
            'description',
            'hostedGames'
        ]
        
class PostSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = [
            'userId',
            'postBody',
            'postType',
            'private',
            'likes'
        ]
        
class GameSerializer(serializers.ModelSerializer):
    class Meta:
        model = Game
        fields = [
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
            'userOneId',
            'userTwoId'
        ]
        
class MessageSerializer(serializers.ModelSerializer):
    class Meta:
        model = Message
        fields = [
            'senderId',
            'receiverId',
            'content'
        ]

class EventToUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = EventToUser
        fields = [
            'userId',
            'eventId'
        ]

class CommentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Comment
        fields = [
            'userId',
            'postId',
            'content'
        ]

class GameToUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = GameToUser
        fields = [
            'userId',
            'gameId',
            'private'
        ]

class HostedGameSerializer(serializers.ModelSerializer):
    class Meta:
        model = HostedGame
        fields = [
            'eventId',
            'gameId',
            'seatsAvailable'
        ]


class ReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = Comment
        fields = [
            'userId',
            'gameId',
            'content'
        ]

class EventToUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = EventToUser
        fields = [
            'userId',
            'gameId'
        ]