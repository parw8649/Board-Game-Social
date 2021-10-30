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