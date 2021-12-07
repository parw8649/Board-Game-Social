"""Backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from rest_framework.authtoken.views import obtain_auth_token
from BoardGameSocialAPI.views import *
from django.conf.urls.static import static
from django.conf import settings
urlpatterns = [
    path('admin/', admin.site.urls),
    path('login/', obtain_auth_token, name="login"),
    path('logout/', logout, name="logout"),
    path('sign_up/', signUpView, name="sign_up"),
    path('api/', include([
        path('user/', UserView.as_view(), name="user"),
        path('profile/', ProfileView.as_view(), name="profile"),
        path('event/', EventView.as_view(), name="event"),
        path('post/', PostView.as_view(), name="post"),
        path('game/', GameView.as_view(), name="game"),
        path('user_to_user/', UserToUserView.as_view(), name="user_to_user"),
        path('message/', MessageView.as_view(), name="message"),
        path('event_to_user/', EventToUserView.as_view(), name="event_to_user"),
        path('comment/', CommentView.as_view(), name="comment"),
        path('game_to_user/', GameToUserView.as_view(), name="game_to_user"),
        path('hosted_game/', HostedGameView.as_view(), name="hosted_game"),
        path('review/', ReviewView.as_view(), name="review"),
        path('hosted_game_to_user/', HostedGameToUserView.as_view(), name="hosted_game_to_user"),
    ]))
]+ static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
