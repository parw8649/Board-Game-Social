from django.shortcuts import render
from django.db.utils import IntegrityError
from django.views.decorators.csrf import csrf_exempt
from .view_manager import *
from .serializer import *
from .models import *


@csrf_exempt
def signUpView(request):
    try:
        body = get_body(request)
        User.objects.create_user(**body)
        status = 200
    except IntegrityError as e:
        body = {"error": str(e)}
        status = 400
    return get_response(body, status=status)


class UserView(DataAccessView):
    serializer_class = UserSerializer
    queryset = User.objects.all()
    model = User


class EventView(DataAccessView):
    serializer_class = EventSerializer
    queryset = Event.objects.all()
    model = Event


class PostView(DataAccessView):
    serializer_class = PostSerializer
    queryset = Post.objects.all()
    model = Post


class GameView(DataAccessView):
    serializer_class = GameSerializer
    queryset = Game.objects.all()
    model = Game


class UserToUserView(DataAccessView):
    serializer_class = UserToUserSerializer
    queryset = UserToUser.objects.all()
    model = UserToUser


class MessageView(DataAccessView):
    serializer_class = MessageSerializer
    queryset = Message.objects.all()
    model = Message


class EventToUserView(DataAccessView):
    serializer_class = EventToUserSerializer
    queryset = Event.objects.all()
    model = EventToUser


class CommentView(DataAccessView):
    serializer_class = CommentSerializer
    queryset = Comment.objects.all()
    model = Comment


class GameToUserView(DataAccessView):
    serializer_class = GameToUserSerializer
    queryset = GameToUser.objects.all()
    model = GameToUser


class HostedGameView(DataAccessView):
    serializer_class = HostedGameSerializer
    queryset = HostedGame.objects.all()
    model = HostedGame


class ReviewView(DataAccessView):
    serializer_class = ReviewSerializer
    queryset = Review.objects.all()
    model = Review


class HostedGameToUserView(DataAccessView):
    serializer_class = HostedGameToUserSerializer
    queryset = HostedGameToUser.objects.all()
    model = HostedGameToUser

