from django.contrib import admin
from .models import *

admin.site.register(Event)
admin.site.register(Profile)
admin.site.register(Post)
admin.site.register(Game)
admin.site.register(UserToUser)
admin.site.register(Message)
admin.site.register(EventToUser)
admin.site.register(Comment)
admin.site.register(GameToUser)
admin.site.register(HostedGame)
admin.site.register(Review)
admin.site.register(HostedGameToUser)
